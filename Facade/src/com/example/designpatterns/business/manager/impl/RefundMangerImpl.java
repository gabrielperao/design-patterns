package com.example.designpatterns.business.manager.impl;

import com.example.designpatterns.business.manager.RefundManager;
import com.example.designpatterns.exception.RefundProcessingFailedException;
import com.example.designpatterns.model.BookingContract;
import com.google.inject.Singleton;
import lombok.NoArgsConstructor;

import java.util.Random;

@Singleton
@NoArgsConstructor
public class RefundMangerImpl implements RefundManager {

    @Override
    public void startRefundingProcess(BookingContract contract) throws RefundProcessingFailedException {
        if (refundFailed()) {
            final String formattedMessage =
                    String.format("Refunding workflow failed for booking contract %s", contract);
            throw new RefundProcessingFailedException(formattedMessage);
        }
    }

    private boolean refundFailed() {
        return (new Random()).nextBoolean();
    }
}
