package io.mattrandom.mappers;

import io.mattrandom.repositories.entities.AssetEntity;
import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.services.dtos.AssetDto;
import io.mattrandom.services.security.dtos.PlainAuthenticationUserDto;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AssetsMapper {

    public AssetEntity toEntity(AssetDto assetDto, UserEntity userEntity) {
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
        if (Objects.nonNull(assetDto.getAssetCategory())) {
            assetEntityBuilder.assetCategory(assetDto.getAssetCategory());
        }
        if (Objects.nonNull(userEntity)) {
            assetEntityBuilder.userEntity(userEntity);
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
        if (Objects.nonNull(assetEntity.getAssetCategory())) {
            assetDtoBuilder.assetCategory(assetEntity.getAssetCategory());
        }
//        if (Objects.nonNull(assetEntity.getUserEntity())) {
//            assetDtoBuilder.user(PlainAuthenticationUserDto.builder()
//                    .id(assetEntity.getUserEntity().getId())
//                    .username(assetEntity.getUserEntity().getUsername())
//                    .build());
//        }

        return assetDtoBuilder.build();
    }
}
