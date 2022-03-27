package io.mattrandom.services.integrations;

import io.mattrandom.enums.RoomType;
import io.mattrandom.repositories.entities.RoomEntity;
import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.services.dtos.RoomDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RoomServiceIntegrationTests extends AbstractIntegrationTestSchema {

    @Test
    void givenRoomObject_whenSavingRoom_thenSaveIntoDB() {
        //given
        saveMockedUserInDB();
        RoomType roomLarge = RoomType.ROOM_AREA_LARGE;
        RoomDto roomDto = RoomDto.builder()
                .cost(BigDecimal.valueOf(1000))
                .roomType(roomLarge)
                .build();

        //when
        RoomDto savedRoom = roomService.saveOrUpdateRoom(roomDto);
        RoomEntity fetchedRoomById = roomRepository.findById(savedRoom.getId()).get();

        //then
        assertThat(roomRepository.findAll()).hasSize(1);
        assertThat(fetchedRoomById.getRoomType()).isEqualTo(roomLarge);
    }

    @Test
    void givenRoomObject_whenUpdatingRoom_thenSaveUpdatedObjectIntoDB() {
        //given
        UserEntity user = saveMockedUserInDB();
        RoomType roomType = RoomType.ROOM_AREA_LARGE;
        BigDecimal roomCost = BigDecimal.valueOf(1000);

        RoomEntity savedRoom = initializingRoomDB(user, roomType, roomCost);

        RoomType roomLargeToUpdate = RoomType.ROOM_AREA_VERY_SMALL;
        BigDecimal roomCostToUpdate = BigDecimal.valueOf(400);
        RoomDto roomDtoToUpdate = RoomDto.builder()
                .id(savedRoom.getId())
                .cost(roomCostToUpdate)
                .roomType(roomLargeToUpdate)
                .build();

        //when
        roomService.saveOrUpdateRoom(roomDtoToUpdate);
        RoomEntity updatedRoom = roomRepository.findById(savedRoom.getId()).get();

        //then
        assertThat(roomRepository.findAll()).hasSize(1);
        assertThat(updatedRoom.getRoomType()).isEqualTo(roomLargeToUpdate);
        assertThat(updatedRoom.getCost()).isEqualTo(roomCostToUpdate);
    }

    @Test
    void givenRoomObjectWithInitialValueOfCost_whenInactivatingRoom_thenShouldReturnRoomWithValueOfCostZero() {
        //given
        UserEntity user = saveMockedUserInDB();
        RoomType roomType = RoomType.ROOM_AREA_LARGE;
        BigDecimal roomCost = BigDecimal.valueOf(1000);

        RoomEntity savedRoom = initializingRoomDB(user, roomType, roomCost);

        //when
        roomService.inactivateRoom(savedRoom.getId());
        RoomEntity fetchedRoomById = roomRepository.findById(savedRoom.getId()).get();

        //then
        assertThat(roomRepository.findAll()).hasSize(1);
        assertThat(fetchedRoomById.getCost()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void givenLackOfRoomObject_whenGettingAllRooms_thenFetchRoomsWithRoomTypesAndCostZero() {
        //given
        saveMockedUserInDB();

        //when
        List<RoomDto> allRooms = roomService.getAllRooms();

        //then
        assertThat(allRooms).hasSize(5);
        assertThat(allRooms.stream().findFirst().get().getCost()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    void givenRoomObject_whenGettingAllRooms_thenFetchRoomsFromDB() {
        //given
        UserEntity user = saveMockedUserInDB();
        RoomType roomType = RoomType.ROOM_AREA_LARGE;
        BigDecimal roomCost = BigDecimal.valueOf(1000);

        RoomEntity savedRoom = initializingRoomDB(user, roomType, roomCost);

        //when
        List<RoomDto> allRooms = roomService.getAllRooms();

        //then
        assertThat(allRooms).hasSize(1);
        assertThat(allRooms.get(0).getCost()).isEqualTo(roomCost);
    }
}
