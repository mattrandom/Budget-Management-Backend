package io.mattrandom.services.dtos;

import io.mattrandom.enums.AssetCategory;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class AssetDto {

    private Long id;
    private BigDecimal amount;
    private LocalDateTime incomeDate;
    private AssetCategory assetCategory;
    private String description;
}
