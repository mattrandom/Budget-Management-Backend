package io.mattrandom.services.dtos;

import io.mattrandom.enums.RoomType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class RoomExtendedDto {

    private Long id;
    private BigDecimal cost;
    private RoomType roomType;
    private List<RentDto> rentsDto;
}
