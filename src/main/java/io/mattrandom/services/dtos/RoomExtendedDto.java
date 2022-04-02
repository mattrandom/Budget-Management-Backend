package io.mattrandom.services.dtos;

import io.mattrandom.enums.RoomType;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class RoomExtendedDto {

    private Long id;

    @NotNull
    private BigDecimal cost;

    @NotNull
    private RoomType roomType;

    private List<RentDto> rentsDto;
}
