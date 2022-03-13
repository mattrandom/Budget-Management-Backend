package io.mattrandom.services;

import io.mattrandom.mappers.AssetsMapper;
import io.mattrandom.repositories.AssetsRepository;
import io.mattrandom.repositories.entities.AssetEntity;
import io.mattrandom.services.dtos.AssetDto;
import io.mattrandom.validators.AssetValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AssetsService {

    private final AssetsRepository assetsRepository;
    private final AssetsMapper assetsMapper;
    private final AssetValidator assetValidator;

    public List<AssetDto> getAllAssets() {
        List<AssetEntity> assetEntities = assetsRepository.findAll();
        return assetEntities.stream()
                .map(assetsMapper::toDto)
                .toList();
    }

    public void addAsset(AssetDto assetDto) {
        assetValidator.validate(assetDto);
        AssetEntity assetEntity = assetsMapper.toEntity(assetDto);
        assetsRepository.save(assetEntity);
    }

    public void deleteAsset(AssetDto assetDto) {
        AssetEntity assetEntity = assetsMapper.toEntity(assetDto);
        assetsRepository.delete(assetEntity);
    }

    public void updateAsset(AssetDto assetDto) {
        Optional<AssetEntity> assetEntity = assetsRepository.findById(assetDto.getId());
        assetEntity.ifPresent(assetEntityOpt -> {
            assetEntityOpt.setAmount(assetDto.getAmount());
            assetsRepository.saveAndFlush(assetEntityOpt);
        });
    }
}
