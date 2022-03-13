package io.mattrandom.mappers;

import io.mattrandom.repositories.entities.AssetEntity;
import io.mattrandom.services.dtos.AssetDto;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AssetsMapper {

    public AssetEntity toEntity(AssetDto assetDto) {
        if (Objects.isNull(assetDto)) {
            return null;
        }

        AssetEntity.AssetEntityBuilder assetEntityBuilder = AssetEntity.builder();

        if (Objects.nonNull(assetDto.getId())) {
            assetEntityBuilder.id(assetDto.getId());
        }
        if (Objects.nonNull(assetDto.getAmount())) {
            assetEntityBuilder.amount(assetDto.getAmount());
        }
        if (Objects.nonNull(assetDto.getIncomeDate())) {
            assetEntityBuilder.incomeDate(assetDto.getIncomeDate());
        }

        return assetEntityBuilder.build();
    }

    public AssetDto toDto(AssetEntity assetEntity) {
        if (Objects.isNull(assetEntity)) {
            return null;
        }

        AssetDto.AssetDtoBuilder assetDtoBuilder = AssetDto.builder();

        if (Objects.nonNull(assetEntity.getId())) {
            assetDtoBuilder.id(assetEntity.getId());
        }
        if (Objects.nonNull(assetEntity.getAmount())) {
            assetDtoBuilder.amount(assetEntity.getAmount());
        }
        if (Objects.nonNull(assetEntity.getIncomeDate())) {
            assetDtoBuilder.incomeDate(assetEntity.getIncomeDate());
        }

        return assetDtoBuilder.build();
    }
}
