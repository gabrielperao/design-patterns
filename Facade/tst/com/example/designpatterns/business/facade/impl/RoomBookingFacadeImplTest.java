package com.example.designpatterns.business.facade.impl;

import com.example.designpatterns.business.manager.InvoiceManager;
import com.example.designpatterns.business.manager.NotificationManager;
import com.example.designpatterns.business.manager.RefundManager;
import com.example.designpatterns.business.manager.ReservationManager;
import com.example.designpatterns.exception.*;
import com.example.designpatterns.model.BookingContract;
import com.example.designpatterns.model.BookingStatus;
import com.example.designpatterns.model.RoomType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RoomBookingFacadeImplTest {

    private static final String CUSTOMER_ID = "CustomerID";
    private static final RoomType ROOM_TYPE = RoomType.SINGLE;
    private static final Date START_DATE;
    private static final Date END_DATE;

    @Mock
    private InvoiceManager invoiceManager;

    @Mock
    private NotificationManager notificationManager;

    @Mock
    private RefundManager refundManager;

    @Mock
    private ReservationManager reservationManager;

    private RoomBookingFacadeImpl roomBookingFacadeImpl;

    private BookingContract defaultContract;

    static {
        final LocalDate startDate = LocalDate.of(2023, 12, 22);
        final LocalDate endDate = LocalDate.of(2024, 1, 2);

        START_DATE = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        END_DATE = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);

        roomBookingFacadeImpl = new RoomBookingFacadeImpl(
                notificationManager,
                reservationManager,
                invoiceManager,
                refundManager
        );

        defaultContract = buildDefaultContract();
    }

    @Test
    public void testBookRoomWhenBookingIsSuccessful() {
        when(reservationManager.reserveRoom(any(BookingContract.class))).thenReturn(buildContractWithConfirmedStatus());
        doNothing().when(invoiceManager).generateInvoice(any(BookingContract.class));
        doNothing().when(notificationManager).notifyCustomerOfSuccessfulRoomBooking(any(BookingContract.class));

        BookingContract updatedContract = roomBookingFacadeImpl.bookRoom(defaultContract);

        assertEquals(BookingStatus.CONFIRMED, updatedContract.getStatus());
        verify(notificationManager, times(1))
                .notifyCustomerOfSuccessfulRoomBooking(any(BookingContract.class));
    }

    @Test
    public void testBookRoomWhenInvoiceGenerationFails() {
        when(reservationManager.reserveRoom(any(BookingContract.class))).thenReturn(buildContractWithConfirmedStatus());
        doThrow(InvoiceGenerationFailedException.class).when(invoiceManager).generateInvoice(any(BookingContract.class));
        doNothing().when(notificationManager).notifyCustomerOfFailedRoomBooking(any(BookingContract.class));

        assertThrows(TerminalException.class, () -> roomBookingFacadeImpl.bookRoom(defaultContract));
        verify(notificationManager, times(1))
                .notifyCustomerOfFailedRoomBooking(any(BookingContract.class));
    }

    @Test
    public void testBookRoomWhenUnavailableRoomAndRefundingProcessSucceeds() {
        when(reservationManager.reserveRoom(any(BookingContract.class)))
                .thenReturn(buildContractWithUnavailableRoomStatus());
        doNothing().when(refundManager).startRefundingProcess(any(BookingContract.class));
        doNothing().when(notificationManager).notifyCustomerOfUnavailableRoom(any(BookingContract.class));

        BookingContract updatedContract = roomBookingFacadeImpl.bookRoom(defaultContract);

        assertEquals(BookingStatus.UNAVAILABLE_ROOM, updatedContract.getStatus());
        verify(notificationManager, times(1))
                .notifyCustomerOfUnavailableRoom(any(BookingContract.class));
    }

    @Test
    public void testBookRoomWhenUnavailableRoomAndRefundingProcessFails() {
        when(reservationManager.reserveRoom(any(BookingContract.class)))
                .thenReturn(buildContractWithUnavailableRoomStatus());
        doThrow(RefundProcessingFailedException.class).when(refundManager).startRefundingProcess(any(BookingContract.class));
        doNothing().when(notificationManager).notifyCustomerOfFailedRoomBooking(any(BookingContract.class));

        assertThrows(TerminalException.class, () -> roomBookingFacadeImpl.bookRoom(defaultContract));
        verify(notificationManager, times(1))
                .notifyCustomerOfFailedRoomBooking(any(BookingContract.class));
    }

    @Test
    public void testCancelBookingWhenCancellationIsSuccesful() {
        when(reservationManager.cancelReservation(any(BookingContract.class)))
                .thenReturn(buildContractWithCancelledStatus());
        doNothing().when(refundManager).startRefundingProcess(any(BookingContract.class));
        doNothing().when(invoiceManager).cancelInvoice(any(BookingContract.class));
        doNothing().when(notificationManager).notifyCustomerOfSuccessfulBookingCancellation(any(BookingContract.class));

        BookingContract updatedContract = roomBookingFacadeImpl.cancelBooking(defaultContract);

        assertEquals(BookingStatus.CANCELLED, updatedContract.getStatus());
        verify(notificationManager, times(1))
                .notifyCustomerOfSuccessfulBookingCancellation(any(BookingContract.class));
    }

    @Test
    public void testCancelBookingWhenCancellationFails() {
        when(reservationManager.cancelReservation(any(BookingContract.class)))
                .thenThrow(ReservationCancellationFailedException.class);
        doNothing().when(notificationManager).notifyCustomerOfFailedBookingCancellation(any(BookingContract.class));

        assertThrows(TerminalException.class, () -> roomBookingFacadeImpl.cancelBooking(defaultContract));
        verify(notificationManager, times(1))
                .notifyCustomerOfFailedBookingCancellation(any(BookingContract.class));
    }

    @Test
    public void testCancelBookingWhenRefundingProcessFails() {
        when(reservationManager.cancelReservation(any(BookingContract.class)))
                .thenReturn(buildContractWithCancelledStatus());
        doThrow(RefundProcessingFailedException.class).when(refundManager).startRefundingProcess(any(BookingContract.class));
        doNothing().when(notificationManager).notifyCustomerOfFailedBookingCancellation(any(BookingContract.class));

        assertThrows(TerminalException.class, () -> roomBookingFacadeImpl.cancelBooking(defaultContract));
        verify(notificationManager, times(1))
                .notifyCustomerOfFailedBookingCancellation(any(BookingContract.class));
    }

    @Test
    public void testCancelBookingWhenInvoiceCancellationFails() {
        when(reservationManager.cancelReservation(any(BookingContract.class)))
                .thenReturn(buildContractWithCancelledStatus());
        doThrow(InvoiceCancellationFailedException.class).when(invoiceManager).cancelInvoice(any(BookingContract.class));
        doNothing().when(notificationManager).notifyCustomerOfFailedBookingCancellation(any(BookingContract.class));

        assertThrows(TerminalException.class, () -> roomBookingFacadeImpl.cancelBooking(defaultContract));
        verify(notificationManager, times(1))
                .notifyCustomerOfFailedBookingCancellation(any(BookingContract.class));
    }

    private BookingContract buildDefaultContract() {
        return BookingContract.builder()
                .customerId(CUSTOMER_ID)
                .roomType(ROOM_TYPE)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .build();
    }

    private BookingContract buildContractWithConfirmedStatus() {
        return BookingContract.builder()
                .customerId(CUSTOMER_ID)
                .roomType(ROOM_TYPE)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .status(BookingStatus.CONFIRMED)
                .build();
    }

    private BookingContract buildContractWithCancelledStatus() {
        return BookingContract.builder()
                .customerId(CUSTOMER_ID)
                .roomType(ROOM_TYPE)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .status(BookingStatus.CANCELLED)
                .build();
    }

    private BookingContract buildContractWithUnavailableRoomStatus() {
        return BookingContract.builder()
                .customerId(CUSTOMER_ID)
                .roomType(ROOM_TYPE)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .status(BookingStatus.UNAVAILABLE_ROOM)
                .build();
    }
}
