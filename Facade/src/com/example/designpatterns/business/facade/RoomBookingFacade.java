package com.example.designpatterns.business.facade;

import com.example.designpatterns.model.BookingContract;

/**
 * Facade for business logic of room booking
 */
public interface RoomBookingFacade {

    /**
     * Tries to book a room
     *
     * @param contract BookingContract containing info about the room booking
     */
    BookingContract bookRoom(BookingContract contract);

    /**
     * Tries to cancel a room booking
     *
     * @param contract BookingContract containing info about the room booking
     */
    BookingContract cancelBooking(BookingContract contract);
}
