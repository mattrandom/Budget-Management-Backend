package io.mattrandom.services;

import io.mattrandom.enums.RoomType;
import io.mattrandom.mappers.RoomMapper;
import io.mattrandom.repositories.RoomRepository;
import io.mattrandom.repositories.entities.RoomEntity;
import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.services.dtos.RoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final UserLoginService userLoginService;

    @Transactional
    public RoomDto saveOrUpdateRoom(RoomDto roomDto) {
        return Objects.isNull(roomDto.getId()) ? saveRoom(roomDto) : updateRoom(roomDto);
    }

    private RoomDto saveRoom(RoomDto roomDto) {
        UserEntity loggedUserEntity = userLoginService.getLoggedUserEntity();
        RoomEntity roomEntity = roomMapper.toEntity(roomDto, loggedUserEntity);
        roomRepository.save(roomEntity);
        return roomMapper.toDto(roomEntity);
    }

    private RoomDto updateRoom(RoomDto roomDto) {
        Optional<RoomEntity> roomEntityOpt = roomRepository.findById(roomDto.getId());
        roomEntityOpt.ifPresent(roomEntity -> roomMapper.toEntityUpdatedByDto(roomEntityOpt.get(), roomDto));
        return roomMapper.toDto(roomEntityOpt.get());
    }


    @Transactional
    public RoomDto inactivateRoom(Long id) {
        Optional<RoomEntity> roomEntityOpt = roomRepository.findById(id);
        roomEntityOpt.ifPresent(roomEntity -> roomEntity.setCost(BigDecimal.ZERO));
        return roomMapper.toDto(roomEntityOpt.get());
    }

    public List<RoomDto> getAllRooms() {
        UserEntity loggedUserEntity = userLoginService.getLoggedUserEntity();
        List<RoomEntity> fetchedRooms = roomRepository.findByUserEntity(loggedUserEntity);

        if (fetchedRooms.size() > 0) {
            return roomMapper.toDtos(fetchedRooms);

        } else {
            return Arrays.stream(RoomType.values()).map(roomType -> RoomDto.builder()
                            .roomType(roomType)
                            .cost(BigDecimal.ZERO)
                            .build())
                    .toList();
        }
    }
}
