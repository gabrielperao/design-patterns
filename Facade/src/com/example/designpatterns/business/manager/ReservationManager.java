package com.example.designpatterns.business.manager;

import com.example.designpatterns.exception.ReservationCancellationFailedException;
import com.example.designpatterns.exception.UnavailableRoomException;
import com.example.designpatterns.model.BookingContract;

public interface ReservationManager {

    /**
     * Reserves a room based on booking contract info
     *
     * @param contract BookingContract with info
     */
    BookingContract reserveRoom(BookingContract contract) throws UnavailableRoomException;

    /**
     * Cancels a room reservation based on booking contract info
     *
     * @param contract BookingContract with info
     */
    BookingContract cancelReservation(BookingContract contract) throws ReservationCancellationFailedException;
}
