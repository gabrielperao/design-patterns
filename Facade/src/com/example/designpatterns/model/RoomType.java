package com.example.designpatterns.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoomType {
    SINGLE("Single"),
    DOUBLE("Double"),
    TWIN("Twin"),
    SUITE("Suite"),
    DELUXE("Deluxe");

    private final String value;
}
