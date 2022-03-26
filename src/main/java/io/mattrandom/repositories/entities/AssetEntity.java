package io.mattrandom.repositories.entities;

import io.mattrandom.enums.AssetCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "assets")
public class AssetEntity extends AbstractEntity {

    private BigDecimal amount;
    private LocalDateTime incomeDate;
    private String description;

    @Enumerated(EnumType.STRING)
    private AssetCategory assetCategory;
}
