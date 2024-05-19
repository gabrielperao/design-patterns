package com.example.designpatterns.business.facade.impl;

import com.example.designpatterns.business.facade.RoomBookingFacade;
import com.example.designpatterns.business.manager.InvoiceManager;
import com.example.designpatterns.business.manager.NotificationManager;
import com.example.designpatterns.business.manager.RefundManager;
import com.example.designpatterns.business.manager.ReservationManager;
import com.example.designpatterns.business.manager.impl.LogManager;
import com.example.designpatterns.exception.*;
import com.example.designpatterns.model.BookingContract;
import com.example.designpatterns.model.BookingStatus;
import com.google.inject.Inject;
import lombok.AllArgsConstructor;

@AllArgsConstructor(onConstructor_ = @__(@Inject))
public class RoomBookingFacadeImpl implements RoomBookingFacade {

    private final NotificationManager notificationManager;
    private final ReservationManager reservationManager;
    private final InvoiceManager invoiceManager;
    private final RefundManager refundManager;

    @Override
    public BookingContract bookRoom(BookingContract contract) throws TerminalException {
        try {
            BookingContract updatedContract = reservationManager.reserveRoom(contract);

            if (BookingStatus.UNAVAILABLE_ROOM.equals(updatedContract.getStatus())) {;
                refundManager.startRefundingProcess(updatedContract);
                notificationManager.notifyCustomerOfUnavailableRoom(updatedContract);
            } else {
                invoiceManager.generateInvoice(updatedContract);
                notificationManager.notifyCustomerOfSuccessfulRoomBooking(updatedContract);
            }

            return updatedContract;

        } catch (InvoiceGenerationFailedException | RefundProcessingFailedException e) {
            LogManager.error(e.getMessage());

            notificationManager.notifyCustomerOfFailedRoomBooking(contract);

            String formattedMessage =
                String.format("Could not complete room booking workflow for contract %s: %s", contract, e.getMessage());
            throw new TerminalException(formattedMessage);
        }
    }

    @Override
    public BookingContract cancelBooking(BookingContract contract) throws TerminalException {
        try {
            BookingContract updatedContract = reservationManager.cancelReservation(contract);

            refundManager.startRefundingProcess(updatedContract);
            invoiceManager.cancelInvoice(updatedContract);
            notificationManager.notifyCustomerOfSuccessfulBookingCancellation(updatedContract);

            return updatedContract;

        } catch (ReservationCancellationFailedException | InvoiceCancellationFailedException |
                 RefundProcessingFailedException e) {
            LogManager.error(e.getMessage());

            notificationManager.notifyCustomerOfFailedBookingCancellation(contract);

            String formattedMessage =
                String.format("Could not complete reservation cancellation workflow for contract %s: %s",
                    contract, e.getMessage());
            throw new TerminalException(formattedMessage);
        }
    }
}
