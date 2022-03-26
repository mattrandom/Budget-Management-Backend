package io.mattrandom.services;

import io.mattrandom.mappers.PropertyMapper;
import io.mattrandom.repositories.PropertyRepository;
import io.mattrandom.repositories.entities.PropertyEntity;
import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.services.dtos.PropertyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
