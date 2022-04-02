package io.mattrandom.mappers;

import io.mattrandom.repositories.entities.RoomEntity;
import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.services.dtos.RoomDto;
import io.mattrandom.services.dtos.RoomExtendedDto;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring", uses = RentMapper.class)
public interface RoomMapper {

    RoomDto toDto(RoomEntity roomEntity);

    @Mapping(target = "userEntity", expression = "java(user)")
    RoomEntity toEntity(RoomDto roomDto, @Context UserEntity user);

    List<RoomDto> toDtos(List<RoomEntity> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userEntity", ignore = true)
    RoomEntity toEntityUpdatedByDto(@MappingTarget RoomEntity roomEntity, RoomDto roomDto);

    @Mapping(source = "rents", target = "rentsDto")
    RoomExtendedDto toExtendedDto(RoomEntity roomEntity);

    @Mapping(source = "rentsDto", target = "rents")
    @Mapping(target = "userEntity", expression = "java(user)")
    RoomEntity toExtendedEntity(RoomExtendedDto roomDto, @Context UserEntity user);

    List<RoomExtendedDto> toExtendedDtos(List<RoomEntity> entities);
}
