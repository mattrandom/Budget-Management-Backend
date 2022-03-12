package io.mattrandom.services;

import io.mattrandom.mappers.AssetsMapper;
import io.mattrandom.repositories.AssetsRepository;
import io.mattrandom.repositories.entities.AssetEntity;
import io.mattrandom.services.dtos.AssetDto;
import io.mattrandom.services.dtos.AssetsDto;
import io.mattrandom.validators.AssetsValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AssetsService {

    private final AssetsRepository assetsRepository;
    private final AssetsMapper assetsMapper;
    private final AssetsValidator assetsValidator;

    public AssetsDto getAllAssets() {
        List<AssetEntity> fetchedAssets = assetsRepository.findAll();

        List<Integer> mappedAssetsIDs = fetchedAssets.stream()
                .map(assetEntity -> assetEntity.getAmount().intValue())
                .toList();

        AssetsDto assetsDto = new AssetsDto();
        assetsDto.setAssetsIds(mappedAssetsIDs);

        return assetsDto;
    }

    public void addAsset(AssetDto assetDto) {
        assetsValidator.validate(assetDto);
        AssetEntity assetEntity = assetsMapper.toEntity(assetDto);
        assetsRepository.save(assetEntity);
    }

    public void deleteAsset(AssetDto assetDto) {
        AssetEntity assetEntity = assetsMapper.toEntity(assetDto);
        assetsRepository.delete(assetEntity);
    }
}
