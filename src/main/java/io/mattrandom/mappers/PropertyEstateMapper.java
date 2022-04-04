package io.mattrandom.mappers;

import io.mattrandom.repositories.entities.PropertyEntity;
import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.services.dtos.PropertyDto;
import io.mattrandom.services.dtos.PropertyExtendedDto;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(
        componentModel = "spring"
)
public interface PropertyEstateMapper {

    @Mapping(source = "isSingleFriendly", target = "singleFriendly")
    PropertyDto toDto(PropertyEntity propertyEntity);

    @Mapping(target = "isSold", expression = "java(false)")
    @Mapping(target = "userEntity", expression = "java(user)")
    @Mapping(source = "singleFriendly", target = "isSingleFriendly")
    PropertyEntity toEntity(PropertyDto propertyDto, @Context UserEntity user);

    List<PropertyDto> toDtos(List<PropertyEntity> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userEntity", ignore = true)
    @Mapping(source = "singleFriendly", target = "isSingleFriendly")
    PropertyEntity toEntityUpdatedByDto(@MappingTarget PropertyEntity propertyEntity, PropertyDto propertyDto);

    @Mapping(source = "rooms", target = "roomsDto")
    @Mapping(source = "isSingleFriendly", target = "singleFriendly")
    PropertyExtendedDto toExtendedDto(PropertyEntity propertyEntity);

    @Mapping(source = "roomsDto", target = "rooms")
    @Mapping(target = "isSold", expression = "java(false)")
    @Mapping(target = "userEntity", expression = "java(user)")
    @Mapping(source = "singleFriendly", target = "isSingleFriendly")
    PropertyEntity toExtendedEntity(PropertyExtendedDto propertyExtendedDto, @Context UserEntity user);

    List<PropertyExtendedDto> toExtendedDtos(List<PropertyEntity> entities);
}
