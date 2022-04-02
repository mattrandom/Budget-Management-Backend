package io.mattrandom.services.dtos;

import io.mattrandom.enums.AssetCategory;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class AssetDto {

    private Long id;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private LocalDateTime incomeDate;

    @NotNull
    private AssetCategory assetCategory;

    @Size(min = 3)
    private String description;
}
