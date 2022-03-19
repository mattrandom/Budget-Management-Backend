package io.mattrandom.services;

import io.mattrandom.enums.AssetCategory;
import io.mattrandom.mappers.AssetsMapper;
import io.mattrandom.repositories.AssetsRepository;
import io.mattrandom.repositories.entities.AssetEntity;
import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.services.dtos.AssetDto;
import io.mattrandom.validators.AssetValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AssetsService {

    private final AssetsRepository assetsRepository;
    private final AssetsMapper assetsMapper;
    private final AssetValidator assetValidator;
    private final UserLoginService userLoginService;

    public List<AssetDto> getAllAssetsByPrincipal() {
        log.debug("Getting all Assets of currently logged in User principal");

        UserEntity principal = getUserEntity();

        List<AssetEntity> assetEntities = assetsRepository.findByUserEntity(principal);
        return assetEntities.stream()
                .map(assetsMapper::toDto)
                .toList();
    }

    public void addAsset(AssetDto assetDto) {
        log.info("Adding single Asset");
        log.debug("AssetDto: " + assetDto);
        assetValidator.validate(assetDto);
        UserEntity userEntity = getUserEntity();
        AssetEntity assetEntity = assetsMapper.toEntity(assetDto, userEntity);

        assetsRepository.save(assetEntity);
        log.info("Asset has just been saved!");
    }

    public void deleteAsset(AssetDto assetDto) {
        log.info("Deleting single Asset");
        log.debug("AssetDto: " + assetDto);
        UserEntity userEntity = getUserEntity();
        AssetEntity assetEntity = assetsMapper.toEntity(assetDto, userEntity);

        assetsRepository.delete(assetEntity);
        log.info("Asset has just been deleted!");
    }

    public void updateAsset(AssetDto assetDto) {
        log.info("Updating single Asset");
        log.debug("AssetDto: " + assetDto);
        Optional<AssetEntity> assetEntity = assetsRepository.findById(assetDto.getId());
        assetEntity.ifPresent(assetEntityOpt -> {
            assetEntityOpt.setAmount(assetDto.getAmount());
            assetsRepository.saveAndFlush(assetEntityOpt);
        });
        log.info("Asset has just been updated!");
    }

    public List<AssetDto> getAllAssetsByCategory(AssetCategory assetCategory) {
        List<AssetEntity> byAssetCategory = assetsRepository.findByAssetCategory(assetCategory);
        return byAssetCategory.stream()
                .map(assetsMapper::toDto)
                .toList();
    }

    private UserEntity getUserEntity() {
        log.info("Fetching logged entity User");
        return userLoginService.getLoggedUserEntity();
    }
}
