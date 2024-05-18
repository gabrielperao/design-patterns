package com.example.designpatterns.business.facade.impl;

import com.example.designpatterns.business.facade.RoomBookingFacade;
import com.example.designpatterns.business.manager.ReservationManager;
import com.example.designpatterns.business.manager.impl.LogManager;
import com.example.designpatterns.exception.*;
import com.example.designpatterns.model.BookingContract;
import com.example.designpatterns.model.BookingStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoomBookingFacadeImpl implements RoomBookingFacade {

    private final NotificationManager notificationManager;
    private final ReservationManager reservationManager;
    private final InvoiceManager invoiceManager;
    private final RefundManager refundManager;

    @Override
    public BookingContract bookRoom(BookingContract contract) throws TerminalException {
        try {
            BookingContract updatedContract = reservationManager.reserveRoom(contract);

            // TODO: InvoiceManager.generateInvoice(updatedContract)
            // TODO: send notification

            return updatedContract;
        } catch (UnavailableRoomException e) {
            LogManager.warn(e.getMessage());

            // TODO: send notification (unavailable_room)
            // TODO: RefundManager.processRefund()
            contract.updateStatus(BookingStatus.UNAVAILABLE_ROOM);

            throw new TerminalException(e);
        } catch (InvoiceGenerationFailedException | RefundProcessingFailedException e) {
            LogManager.error(e.getMessage());

            // TODO: send notification
            // Extra logic to send to DLQ? Not applicable for this project

            throw new TerminalException(e);
        }
    }

    @Override
    public BookingContract cancelBooking(BookingContract contract) throws TerminalException {
        try {
            BookingContract updatedContract = reservationManager.cancelReservation(contract);

            // TODO: RefundManager.processRefund()
            // TODO: InvoiceManager.cancelInvoice()
            // TODO: send notification

            return updatedContract;
        } catch (ReservationCancellationFailedException | InvoiceCancellationFailedException e) {
            LogManager.error(e.getMessage());

            // send notification

            throw new TerminalException(e);
        }
    }
}
