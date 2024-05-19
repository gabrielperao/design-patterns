package com.example.designpatterns.business.manager;

import com.example.designpatterns.model.BookingContract;

public interface NotificationManager {

    /**
     * Sends a notification to the customer confirming the reservation and providing extra information
     *
     * @param contract BookingContract with info
     */
    void notifyCustomerOfSuccessfulRoomBooking(BookingContract contract);

    /**
     * Sends a notification to the customer informing that the room requested by the contract is no longer available
     *
     * @param contract BookingContract with info
     */
    void notifyCustomerOfUnavailableRoom(BookingContract contract);

    /**
     * Sends a notification to the customer informing that something went wrong and the reservation could not be
     * completed
     *
     * @param contract BookingContract with info
     */
    void notifyCustomerOfFailedRoomBooking(BookingContract contract);

    /**
     * Sends a notification to the customer confirming the reservation cancellation
     *
     * @param contract BookingContract with info
     */
    void notifyCustomerOfSuccessfulBookingCancellation(BookingContract contract);

    /**
     * Sends a notification to the customer informing that something went wrong and the reservation cancellation
     * could not be completed
     *
     * @param contract BookingContract with info
     */
    void notifyCustomerOfFailedBookingCancellation(BookingContract contract);
}
