package com.example.designpatterns.business.manager.impl;

import com.example.designpatterns.business.manager.InvoiceManager;
import com.example.designpatterns.exception.InvoiceCancellationFailedException;
import com.example.designpatterns.exception.InvoiceGenerationFailedException;
import com.example.designpatterns.model.BookingContract;
import com.google.inject.Singleton;
import lombok.NoArgsConstructor;

import java.util.Random;

@Singleton
@NoArgsConstructor
public class InvoiceManagerImpl implements InvoiceManager {

    @Override
    public void generateInvoice(BookingContract contract) throws InvoiceGenerationFailedException {
        if (invoiceHasFailed()) {
            final String formattedMessage =
                    String.format("invoice generation failed for booking contract %s", contract);
            throw new InvoiceGenerationFailedException(formattedMessage);
        }
    }

    @Override
    public void cancelInvoice(BookingContract contract) throws InvoiceCancellationFailedException {
        if (invoiceHasFailed()) {
            final String formattedMessage =
                    String.format("invoice cancellation failed for booking contract %s", contract);
            throw new InvoiceCancellationFailedException(formattedMessage);
        }
    }

    private boolean invoiceHasFailed() {
        return (new Random()).nextBoolean();
    }
}
