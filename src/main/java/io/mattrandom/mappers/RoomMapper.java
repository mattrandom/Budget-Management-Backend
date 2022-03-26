package io.mattrandom.mappers;

import io.mattrandom.repositories.entities.RoomEntity;
import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.services.dtos.RoomDto;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface RoomMapper {

    RoomDto toDto(RoomEntity roomEntity);

    @Mapping(target = "userEntity", expression = "java(user)")
    RoomEntity toEntity(RoomDto roomDto, @Context UserEntity user);

    List<RoomDto> toDtos(List<RoomEntity> entities);
}
