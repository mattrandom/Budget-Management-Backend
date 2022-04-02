package io.mattrandom.services;

import io.mattrandom.mappers.RentMapper;
import io.mattrandom.repositories.RentRepository;
import io.mattrandom.repositories.RoomRepository;
import io.mattrandom.repositories.entities.RentEntity;
import io.mattrandom.repositories.entities.RoomEntity;
import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.services.dtos.RentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final UserLoginService userLoginService;
    private final RentRepository rentRepository;
    private final RoomRepository roomRepository;
    private final RentMapper rentMapper;

    @Transactional
    public RentDto addRoomToRental(Long roomId, RentDto rentDto) {
        UserEntity loggedUserEntity = userLoginService.getLoggedUserEntity();
        RentEntity rentEntity = rentMapper.toEntity(rentDto, loggedUserEntity);
        rentEntity.setIsRent(true);
        RoomEntity roomEntity = roomRepository.findById(roomId).orElseThrow();
        roomEntity.addRent(rentEntity);
        return rentMapper.toDto(rentEntity);
    }

    public List<RentDto> getAllRentals() {
        List<RentEntity> entities = rentRepository.findAll();
        return rentMapper.toDtos(entities);
    }

    @Transactional
    public RentDto removeRoomFromRental(Long roomId, Long rentId) {
        RoomEntity roomEntity = roomRepository.findById(roomId).orElseThrow();
        RentEntity rentEntity = rentRepository.findById(rentId).orElseThrow();
        roomEntity.removeRent(rentEntity);
        return rentMapper.toDto(rentEntity);
    }
}
