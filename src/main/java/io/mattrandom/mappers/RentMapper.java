package io.mattrandom.mappers;

import io.mattrandom.repositories.entities.RentEntity;
import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.services.dtos.RentDto;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(
        componentModel = "spring"
)
public interface RentMapper {

    @Mapping(source = "room", target = "roomDto")
    RentDto toDto(RentEntity entity);

    List<RentDto> toDtos(List<RentEntity> entities);

    @Mapping(source = "roomDto", target = "room")
    @Mapping(target = "isRent", expression = "java(false)")
    @Mapping(target = "isPaid", expression = "java(false)")
    @Mapping(target = "userEntity", expression = "java(user)")
    RentEntity toEntity(RentDto dto, @Context UserEntity user);
}
