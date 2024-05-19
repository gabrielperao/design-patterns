package com.example.designpatterns.business.manager;

import com.example.designpatterns.exception.RefundProcessingFailedException;
import com.example.designpatterns.model.BookingContract;

public interface RefundManager {
    /**
     * Starts refunding workflow based on booking contract
     *
     * @param contract BookingContract with info
     */
    void startRefundProcess(BookingContract contract) throws RefundProcessingFailedException;
}
