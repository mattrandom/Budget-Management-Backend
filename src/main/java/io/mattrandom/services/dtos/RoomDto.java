package io.mattrandom.services.dtos;

import io.mattrandom.enums.RoomType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class RoomDto {

    private Long id;
    private BigDecimal cost;
    private RoomType roomType;
}
