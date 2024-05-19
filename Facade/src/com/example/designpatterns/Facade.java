package com.example.designpatterns;

import com.example.designpatterns.business.facade.RoomBookingFacade;
import com.example.designpatterns.exception.TerminalException;
import com.example.designpatterns.model.BookingContract;
import com.example.designpatterns.model.RoomType;
import com.example.designpatterns.module.AppModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

public class Facade {

    private static final BookingContract contract;
    private static final String CUSTOMER_ID = "CustomerID";
    private static final RoomType ROOM_TYPE = RoomType.SINGLE;
    private static final Date START_DATE;
    private static final Date END_DATE;

    private static RoomBookingFacade roomBookingFacade;

    // Example contract building for this Facade example implementation
    static {
        final LocalDate startDate = LocalDate.of(2023, 12, 22);
        final LocalDate endDate = LocalDate.of(2024, 1, 2);

        START_DATE = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        END_DATE = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        contract = buildContract();
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AppModule());

        roomBookingFacade = injector.getInstance(RoomBookingFacade.class);

        start();
    }

    private static void start() {
        Optional<BookingContract> updatedContract = Optional.ofNullable(book());

        updatedContract.ifPresent(Facade::cancel);
    }

    private static BookingContract book() {
        try {
            final BookingContract updatedContract = roomBookingFacade.bookRoom(contract);

            String formattedMessage =
                String.format(
                    "Successfully finished room booking workflow! Updated Room Booking contract: %s",
                    updatedContract);
            System.out.println(formattedMessage);
            return updatedContract;
        } catch (TerminalException e) {
            // (...) Extra logic to treat failure, send to DLQ etc
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static void cancel(BookingContract updatedContract) {
        try {
            final BookingContract cancelledContract = roomBookingFacade.cancelBooking(updatedContract);

            String formattedMessage =
                String.format(
                    "Successfully finished reservation cancellation workflow! Updated Room Booking contract: %s",
                    cancelledContract);
            System.out.println(formattedMessage);
        } catch (TerminalException e) {
            // (...) Extra logic to treat failure, send to DLQ etc
            System.out.println(e.getMessage());
        }
    }

    private static BookingContract buildContract() {
        return BookingContract.builder()
                .customerId(CUSTOMER_ID)
                .roomType(ROOM_TYPE)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .build();
    }
}