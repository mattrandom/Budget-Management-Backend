package io.mattrandom.services;

import io.mattrandom.mappers.PropertyMapper;
import io.mattrandom.repositories.PropertyRepository;
import io.mattrandom.repositories.entities.PropertyEntity;
import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.services.dtos.PropertyDto;
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
}
