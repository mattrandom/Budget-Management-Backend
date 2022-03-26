package io.mattrandom.mappers;

import io.mattrandom.repositories.entities.PropertyEntity;
import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.services.dtos.PropertyDto;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@Mapper(componentModel = "spring")
public interface PropertyMapper {

    PropertyDto toDto(PropertyEntity propertyEntity);

    @Mapping(target = "userEntity", expression = "java(user)")
    PropertyEntity toEntity(PropertyDto propertyDto, @Context UserEntity user);

    List<PropertyDto> toDtos(List<PropertyEntity> entities);
}
