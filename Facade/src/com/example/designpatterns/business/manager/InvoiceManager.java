package com.example.designpatterns.business.manager;

import com.example.designpatterns.exception.InvoiceCancellationFailedException;
import com.example.designpatterns.exception.InvoiceGenerationFailedException;
import com.example.designpatterns.model.BookingContract;

public interface InvoiceManager {

    /**
     * Generates an invoice for a room booking based on booking contract
     *
     * @param contract BookingContract with info
     */
    void generateInvoice(BookingContract contract) throws InvoiceGenerationFailedException;

    /**
     * Cancels an invoice for a room booking based on booking contract
     *
     * @param contract BookingContract with info
     */
    void cancelInvoice(BookingContract contract) throws InvoiceCancellationFailedException;
}
