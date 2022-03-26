package io.mattrandom.mappers;

import io.mattrandom.repositories.entities.PropertyEntity;
import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.services.dtos.PropertyDto;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface PropertyMapper {

    PropertyDto toDto(PropertyEntity propertyEntity);

    @Mapping(target = "userEntity", expression = "java(user)")
    PropertyEntity toEntity(PropertyDto propertyDto, @Context UserEntity user);

    List<PropertyDto> toDtos(List<PropertyEntity> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userEntity", ignore = true)
    PropertyEntity toEntityUpdatedByDto(@MappingTarget PropertyEntity propertyEntity, PropertyDto propertyDto);
}
