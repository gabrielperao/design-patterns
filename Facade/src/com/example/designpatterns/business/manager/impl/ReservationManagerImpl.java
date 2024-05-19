package com.example.designpatterns.business.manager.impl;

import com.example.designpatterns.business.manager.ReservationManager;
import com.example.designpatterns.exception.ReservationCancellationFailedException;
import com.example.designpatterns.exception.RoomNotFoundException;
import com.example.designpatterns.exception.UnavailableRoomException;
import com.example.designpatterns.model.BookingContract;
import com.example.designpatterns.model.BookingStatus;
import com.google.inject.Singleton;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Singleton
@NoArgsConstructor
public class ReservationManagerImpl implements ReservationManager {

    @Override
    public BookingContract reserveRoom(BookingContract contract) throws UnavailableRoomException {
        // some logic to reserve room and get its ID
        final Optional<String> roomIdOptional = simulateReservation();

        if (roomIdOptional.isPresent()) {
            String roomId = roomIdOptional.get();
            contract.updateRoomId(roomId);
            contract.updateStatus(BookingStatus.RESERVED);
        } else {
            final String formattedMessage =
                    String.format("No room available for booking contract %s", contract);
            LogManager.warn(formattedMessage);

            contract.updateStatus(BookingStatus.UNAVAILABLE_ROOM);
        }
        return contract;
    }

    @Override
    public BookingContract cancelReservation(BookingContract contract) throws ReservationCancellationFailedException {
        try {
            simulateReservationCancellation(contract);
            contract.updateStatus(BookingStatus.CANCELLED);
            return contract;
        } catch (RoomNotFoundException e) {
            final String formattedMessage =
                    String.format("Reservation cancellation failed for booking contract %s: %s",
                            contract,
                            e.getMessage());
            throw new ReservationCancellationFailedException(formattedMessage);
        }
    }

    private Optional<String> simulateReservation() {
        boolean isRoomAvailable = (new Random()).nextBoolean();

        if (isRoomAvailable) {
            // (...)
            // Extra logic to compute reservation, not applicable for this project
            // (...)
            String roomId = UUID.randomUUID().toString();
            return Optional.of(roomId);
        } else {
            return Optional.empty();
        }
    }

    private void simulateReservationCancellation(final BookingContract contract) throws RoomNotFoundException {
        // Extra logic to cancel reservation, not applicable for this project
        final boolean couldFindRoomByID = ((new Random()).nextBoolean() && contract.getRoomId() != null);
        if (!couldFindRoomByID) {
            final String formattedMessage = String.format("Could not find room with roomId %s", contract.getRoomId());
            throw new RoomNotFoundException(formattedMessage);
        }
    }
}
