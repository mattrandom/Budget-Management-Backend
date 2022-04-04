package io.mattrandom.services;

import io.mattrandom.exceptions.PropertyIsNotFoundException;
import io.mattrandom.exceptions.RoomNotFoundException;
import io.mattrandom.mappers.PropertyEstateMapper;
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
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final PropertyEstateMapper propertyEstateMapper;
    private final UserLoginService userLoginService;
    private final RoomMapper roomMapper;
    private final RoomRepository roomRepository;

    public PropertyDto addProperty(PropertyDto propertyDto) {
        UserEntity loggedUserEntity = userLoginService.getLoggedUserEntity();
        PropertyEntity propertyEntity = propertyEstateMapper.toEntity(propertyDto, loggedUserEntity);
        propertyRepository.save(propertyEntity);
        return propertyEstateMapper.toDto(propertyEntity);
    }

    public List<PropertyDto> getAllProperties() {
        UserEntity loggedUserEntity = userLoginService.getLoggedUserEntity();
        List<PropertyEntity> entities = propertyRepository.findByUserEntity(loggedUserEntity);
        return propertyEstateMapper.toDtos(entities);
    }

    @Transactional
    public PropertyDto updateProperty(PropertyDto propertyDto) {
        PropertyEntity fetchedEntity = propertyRepository.findById(propertyDto.getId())
                .orElseThrow(() -> new PropertyIsNotFoundException(propertyDto.getId()));
        PropertyEntity updatedEntity = propertyEstateMapper.toEntityUpdatedByDto(fetchedEntity, propertyDto);
        return propertyEstateMapper.toDto(updatedEntity);
    }

    public void deleteProperty(PropertyDto propertyDto) {
        PropertyEntity fetchedEntity = propertyRepository.findById(propertyDto.getId())
                .orElseThrow(() -> new PropertyIsNotFoundException(propertyDto.getId()));
        propertyRepository.delete(fetchedEntity);
    }

    @Transactional
    public PropertyExtendedDto addRoomToProperty(Long id, RoomDto roomDto) {
        UserEntity loggedUserEntity = userLoginService.getLoggedUserEntity();
        PropertyEntity fetchedPropertyEntity = propertyRepository.findById(id)
                .orElseThrow(() -> new PropertyIsNotFoundException(id));

        RoomEntity roomEntity = roomMapper.toEntity(roomDto, loggedUserEntity);
        fetchedPropertyEntity.getRooms().add(roomEntity);

        return propertyEstateMapper.toExtendedDto(fetchedPropertyEntity);
    }

    public List<PropertyExtendedDto> getAllPropertiesWithRooms(Boolean isSold) {
        UserEntity loggedUserEntity = userLoginService.getLoggedUserEntity();
        List<PropertyEntity> entities = propertyRepository.findByUserEntityAndSold(loggedUserEntity, isSold);
        return propertyEstateMapper.toExtendedDtos(entities);
    }

    @Transactional
    public void deleteRoomFromProperty(Long propertyId, Long roomId) {
        PropertyEntity fetchedPropertyEntity = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new PropertyIsNotFoundException(propertyId));

        RoomEntity fetchedRoomEntity = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(roomId));

        fetchedPropertyEntity.getRooms().remove(fetchedRoomEntity);
    }

    @Transactional
    public void setPropertyAsSold(Long id) {
        UserEntity loggedUserEntity = userLoginService.getLoggedUserEntity();
        propertyRepository.updatePropertyAsSold(loggedUserEntity, id);
    }
}
