package io.mattrandom.services.integrations;

import io.mattrandom.enums.RoomType;
import io.mattrandom.services.dtos.RoomDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class RoomServiceIntegrationTests extends AbstractIntegrationTestSchema {

    @Test
    void givenRoomObject_whenAddRoom_thenSaveIntoDB() {
        //given
        saveMockedUserInDB();
        RoomDto roomDto = RoomDto.builder()
                .cost(BigDecimal.valueOf(1000))
                .roomType(RoomType.ROOM_AREA_LARGE)
                .build();

        //when
        roomService.addRoom(roomDto);

        //then
        assertThat(roomRepository.findAll()).hasSize(1);
    }
}
