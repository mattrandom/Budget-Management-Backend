package io.mattrandom.services;

import io.mattrandom.mappers.RoomMapper;
import io.mattrandom.repositories.RoomRepository;
import io.mattrandom.repositories.entities.RoomEntity;
import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.services.dtos.RoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final UserLoginService userLoginService;

    public RoomDto addRoom(RoomDto roomDto) {
        UserEntity loggedUserEntity = userLoginService.getLoggedUserEntity();
        RoomEntity roomEntity = roomMapper.toEntity(roomDto, loggedUserEntity);
        roomRepository.save(roomEntity);
        return roomMapper.toDto(roomEntity);
    }
}
