package io.mattrandom.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoomType {

    ROOM_AREA_VERY_SMALL("Very small area"),
    ROOM_AREA_SMALL("Small area"),
    ROOM_AREA_MEDIUM("Medium area"),
    ROOM_AREA_LARGE("Large area"),
    ROOM_AREA_HUGE("Huge area");

    private final String description;
}
