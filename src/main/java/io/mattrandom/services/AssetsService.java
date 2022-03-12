package io.mattrandom.services;

import io.mattrandom.mappers.AssetsMapper;
import io.mattrandom.repositories.AssetsRepository;
import io.mattrandom.repositories.entities.AssetEntity;
import io.mattrandom.services.dtos.AssetDto;
import io.mattrandom.services.dtos.AssetsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AssetsService {

    private final AssetsRepository assetsRepository;
    private final AssetsMapper assetsMapper;

    public AssetsDto getAllAssets() {
        List<AssetEntity> fetchedAssets = assetsRepository.findAll();

        List<Integer> mappedAssetsIDs = fetchedAssets.stream()
                .map(assetEntity -> assetEntity.getAmount().intValue())
                .toList();

        AssetsDto assetsDto = new AssetsDto();
        assetsDto.setAssetsIds(mappedAssetsIDs);

        return assetsDto;
    }

    public void addAsset(Integer assetNumber) {
        AssetDto assetDto = new AssetDto();
        assetDto.setAmount(BigDecimal.valueOf(assetNumber));

        AssetEntity assetEntity = assetsMapper.toEntity(assetDto);

        assetsRepository.save(assetEntity);
    }
}
