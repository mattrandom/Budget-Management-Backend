package io.mattrandom.services.dtos;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetDto {

    private Long id;
    private BigDecimal amount;
}
