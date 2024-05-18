package com.example.designpatterns.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.Date;

@Getter
@Builder
@ToString
public class BookingContract {

    @NonNull
    String customerId;

    String roomId;

    @NonNull
    RoomType roomType;

    @NonNull
    Date startDate;

    @NonNull
    Date endDate;

    @Builder.Default
    @NonNull
    BookingStatus status = BookingStatus.PENDING;

    public void updateRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void updateStatus(BookingStatus status) {
        this.status = status;
    }
}
