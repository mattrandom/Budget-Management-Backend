package io.mattrandom.services;

import io.mattrandom.enums.OtherErrorCodes;
import io.mattrandom.enums.RoomType;
import io.mattrandom.exceptions.PropertyIsNotFoundException;
import io.mattrandom.exceptions.RoomNotFoundException;
import io.mattrandom.mappers.PropertyEstateMapper;
import io.mattrandom.mappers.RoomMapper;
import io.mattrandom.repositories.PropertyRepository;
import io.mattrandom.repositories.RoomRepository;
import io.mattrandom.repositories.entities.PropertyEntity;
import io.mattrandom.repositories.entities.RoomEntity;
import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.services.dtos.PropertyExtendedDto;
import io.mattrandom.services.dtos.RoomDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class PropertyServiceTests {

    @Spy
    private PropertyEstateMapper propertyEstateMapper = Mappers.getMapper(PropertyEstateMapper.class);
    @Spy
    private RoomMapper roomMapper = Mappers.getMapper(RoomMapper.class);;

    @Mock
    private PropertyRepository propertyRepository;
    @Mock
    private UserLoginService userLoginService;
    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private PropertyService propertyService;

    @Test
    void shouldAddRoomToAlreadyExistedProperty() {
        //given
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("principal");
        userEntity.setPassword("pass");

        PropertyEntity propertyEntity = PropertyEntity.builder()
                .id(1L)
                .postalCode("66-666")
                .city("BigCity")
                .street("Long Street")
                .isSingleFriendly(false)
                .isSold(false)
                .house("Flat")
                .userEntity(userEntity)
                .rooms(new ArrayList<>())
                .build();

        RoomDto roomDto = RoomDto.builder()
                .cost(BigDecimal.TEN)
                .roomType(RoomType.ROOM_AREA_LARGE)
                .build();

        given(propertyRepository.findById(anyLong())).willReturn(Optional.of(propertyEntity));

        //when
        PropertyExtendedDto propertyExtendedDto = propertyService.addRoomToProperty(propertyEntity.getId(), roomDto);

        //then
        assertThat(propertyExtendedDto.getRoomsDto()).isNotNull();
        assertThat(propertyExtendedDto.getRoomsDto().get(0)).isEqualTo(roomDto);
    }

    @Test
    void shouldThrowExceptionWhenPropertyIsNotFound() {
        //given
        RoomDto roomDto = RoomDto.builder()
                .cost(BigDecimal.TEN)
                .roomType(RoomType.ROOM_AREA_LARGE)
                .build();

        given(propertyRepository.findById(anyLong())).willReturn(Optional.empty());

        //when
        var propertyIsNotFoundException = assertThrows(PropertyIsNotFoundException.class, () -> propertyService.addRoomToProperty(2L, roomDto));

        //then
        assertThat(propertyIsNotFoundException.getErrorCode()).isEqualTo(OtherErrorCodes.PROPERTY_ERROR_CODE.getErrorCode());
        assertThat(propertyIsNotFoundException.getMessage()).isEqualTo("Property with id=2 is not found");
    }

    @Test
    void shouldRemoveRoomFromProperty() {
        //given
        Long roomId = 1L;
        Long propertyId = 1L;

        RoomEntity roomEntity = RoomEntity.builder()
                .id(roomId)
                .cost(BigDecimal.TEN)
                .roomType(RoomType.ROOM_AREA_LARGE)
                .build();

        PropertyEntity propertyEntity = PropertyEntity.builder()
                .id(propertyId)
                .postalCode("66-666")
                .city("BigCity")
                .street("Long Street")
                .isSingleFriendly(false)
                .isSold(false)
                .house("Flat")
                .rooms(new ArrayList<>(List.of(roomEntity)))
                .build();

        given(roomRepository.findById(anyLong())).willReturn(Optional.of(roomEntity));
        given(propertyRepository.findById(anyLong())).willReturn(Optional.of(propertyEntity));

        //when
        propertyService.deleteRoomFromProperty(propertyId, roomId);

        //then
        assertThat(propertyEntity.getRooms()).isEmpty();
    }

    @Test
    void shouldThrowExceptionWhenPropertyIsNotFoundWhileDeleting() {
        //given
        Long propertyId = 0L;
        Long roomId = 1L;

        given(propertyRepository.findById(anyLong())).willReturn(Optional.empty());

        //when
        var propertyIsNotFoundException = assertThrows(PropertyIsNotFoundException.class, () -> propertyService.deleteRoomFromProperty(propertyId, roomId));

        //then
        then(roomRepository).should(times(0)).findById(anyLong());

        assertThat(propertyIsNotFoundException.getErrorCode()).isEqualTo(OtherErrorCodes.PROPERTY_ERROR_CODE.getErrorCode());
        assertThat(propertyIsNotFoundException.getMessage()).isEqualTo("Property with id=0 is not found");
    }

    @Test
    void shouldThrowExceptionWhenRoomIsNotFoundWhileDeleting() {
        //given
        Long roomId = 0L;
        Long propertyId = 1L;

        PropertyEntity propertyEntity = PropertyEntity.builder()
                .id(propertyId)
                .postalCode("66-666")
                .city("BigCity")
                .street("Long Street")
                .isSingleFriendly(false)
                .isSold(false)
                .house("Flat")
                .rooms(new ArrayList<>())
                .build();

        given(propertyRepository.findById(anyLong())).willReturn(Optional.of(propertyEntity));
        given(roomRepository.findById(anyLong())).willReturn(Optional.empty());

        //when
        var propertyIsNotFoundException = assertThrows(RoomNotFoundException.class, () -> propertyService.deleteRoomFromProperty(propertyId, roomId));

        //then
        assertThat(propertyIsNotFoundException.getErrorCode()).isEqualTo(OtherErrorCodes.ROOM_ERROR_CODE.getErrorCode());
        assertThat(propertyIsNotFoundException.getMessage()).isEqualTo("Room with id=0 is not found");
    }
}
