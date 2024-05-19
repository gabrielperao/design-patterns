package com.example.designpatterns.business.manager.impl;

import com.example.designpatterns.business.manager.NotificationManager;
import com.example.designpatterns.model.BookingContract;
import com.google.inject.Singleton;
import lombok.NoArgsConstructor;

@Singleton
@NoArgsConstructor
public class NotificationManagerImpl implements NotificationManager {
    @Override
    public void notifyCustomerOfSuccessfulRoomBooking(BookingContract contract) {
        ;
    }

    @Override
    public void notifyCustomerOfUnavailableRoom(BookingContract contract) {
        ;
    }

    @Override
    public void notifyCustomerOfFailedRoomBooking(BookingContract contract) {
        ;
    }

    @Override
    public void notifyCustomerOfSuccessfulBookingCancellation(BookingContract contract) {
        ;
    }

    @Override
    public void notifyCustomerOfFailedBookingCancellation(BookingContract contract) {
        ;
    }
}
