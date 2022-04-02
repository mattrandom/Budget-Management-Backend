package io.mattrandom.services;

import io.mattrandom.mappers.PropertyMapper;
import io.mattrandom.mappers.RoomMapper;
import io.mattrandom.repositories.PropertyRepository;
import io.mattrandom.repositories.RoomRepository;
import io.mattrandom.repositories.entities.PropertyEntity;
import io.mattrandom.repositories.entities.RoomEntity;
import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.services.dtos.PropertyDto;
import io.mattrandom.services.dtos.PropertyExtendedDto;
import io.mattrandom.services.dtos.RoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final PropertyMapper propertyMapper;
    private final UserLoginService userLoginService;
    private final RoomMapper roomMapper;
    private final RoomRepository roomRepository;

    public PropertyDto addProperty(PropertyDto propertyDto) {
        UserEntity loggedUserEntity = userLoginService.getLoggedUserEntity();
        PropertyEntity propertyEntity = propertyMapper.toEntity(propertyDto, loggedUserEntity);
        propertyRepository.save(propertyEntity);
        return propertyMapper.toDto(propertyEntity);
    }

    public List<PropertyDto> getAllProperties() {
        UserEntity loggedUserEntity = userLoginService.getLoggedUserEntity();
        List<PropertyEntity> entities = propertyRepository.findByUserEntity(loggedUserEntity);
        return propertyMapper.toDtos(entities);
    }

    @Transactional
    public PropertyDto updateProperty(PropertyDto propertyDto) {
        Optional<PropertyEntity> propertyEntityOpt = propertyRepository.findById(propertyDto.getId());
        propertyEntityOpt.ifPresent(propertyEntity -> propertyMapper.toEntityUpdatedByDto(propertyEntityOpt.get(), propertyDto));
        return propertyMapper.toDto(propertyEntityOpt.get());
    }

    public void deleteProperty(PropertyDto propertyDto) {
        Optional<PropertyEntity> propertyEntityOpt = propertyRepository.findById(propertyDto.getId());
        propertyEntityOpt.ifPresent(propertyRepository::delete);
    }

    @Transactional
    public PropertyExtendedDto addRoomToProperty(Long id, RoomDto roomDto) {
        UserEntity loggedUserEntity = userLoginService.getLoggedUserEntity();
        Optional<PropertyEntity> propertyIdOpt = propertyRepository.findById(id);

        if (propertyIdOpt.isPresent()) {
            RoomEntity roomEntity = roomMapper.toEntity(roomDto, loggedUserEntity);
            PropertyEntity fetchedProperty = propertyIdOpt.get();
            fetchedProperty.getRooms().add(roomEntity);
        }

        return propertyMapper.toExtendedDto(propertyIdOpt.get());
    }

    public List<PropertyExtendedDto> getAllPropertiesWithRooms(Boolean isSold) {
        UserEntity loggedUserEntity = userLoginService.getLoggedUserEntity();
        List<PropertyEntity> entities = propertyRepository.findByUserEntityAndSold(loggedUserEntity, isSold);
        return propertyMapper.toExtendedDtos(entities);
    }

    @Transactional
    public void deleteRoomFromProperty(Long propertyId, Long roomId) {
        Optional<PropertyEntity> propertyOpt = propertyRepository.findById(propertyId);
        Optional<RoomEntity> roomOpt = roomRepository.findById(roomId);

        if (propertyOpt.isPresent()) {
            PropertyEntity propertyEntity = propertyOpt.get();
            RoomEntity roomEntity = roomOpt.get();
            propertyEntity.getRooms().remove(roomEntity);
        }
    }

    @Transactional
    public void setPropertyAsSold(Long id) {
        UserEntity loggedUserEntity = userLoginService.getLoggedUserEntity();
        propertyRepository.updatePropertyAsSold(loggedUserEntity, id);
    }
}
