package io.mattrandom.services.dtos;

import io.mattrandom.enums.RoomType;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
public class RoomDto {

    private Long id;

    @NotNull
    private BigDecimal cost;

    @NotNull
    private RoomType roomType;
}
