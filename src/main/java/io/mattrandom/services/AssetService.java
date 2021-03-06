package io.mattrandom.services;

import io.mattrandom.enums.AssetCategory;
import io.mattrandom.enums.FilterSpecificationEnum;
import io.mattrandom.mappers.AssetMapper;
import io.mattrandom.repositories.AssetRepository;
import io.mattrandom.repositories.entities.AssetEntity;
import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.services.dtos.AssetDto;
import io.mattrandom.validators.AssetValidator;
import io.mattrandom.validators.filters.strategy.RepositoryFilterStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssetService {

    private final AssetRepository assetRepository;
    private final AssetMapper assetMapper;
    private final AssetValidator assetValidator;
    private final UserLoginService userLoginService;
    private final RepositoryFilterStrategy<AssetEntity> filterSpecificRepository;

    public List<AssetDto> getAllAssetsByPrincipal() {
        log.debug("Getting all Assets of the currently logged in principal user");

        UserEntity principal = getUserEntity();

        List<AssetEntity> assetEntities = assetRepository.findByUserEntity(principal);
        return assetEntities.stream()
                .map(assetMapper::toDto)
                .toList();
    }

    public AssetDto addAsset(AssetDto assetDto) {
        log.info("Adding single Asset");
        log.debug("AssetDto: " + assetDto);
        assetValidator.validate(assetDto);
        UserEntity userEntity = getUserEntity();
        AssetEntity assetEntity = assetMapper.toEntity(assetDto, userEntity);

        assetRepository.save(assetEntity);
        log.info("Asset has just been saved!");
        return assetMapper.toDto(assetEntity);
    }

    public void deleteAsset(AssetDto assetDto) {
        log.info("Deleting single Asset");
        log.debug("AssetDto: " + assetDto);
        UserEntity userEntity = getUserEntity();
        AssetEntity assetEntity = assetMapper.toEntity(assetDto, userEntity);

        assetRepository.delete(assetEntity);
        log.info("Asset has just been deleted!");
    }

    public AssetDto updateAsset(AssetDto assetDto) {
        log.info("Updating single Asset");
        log.debug("AssetDto: " + assetDto);
        Optional<AssetEntity> assetEntity = assetRepository.findById(assetDto.getId());
        assetEntity.ifPresent(assetEntityOpt -> {
            assetEntityOpt.setAmount(assetDto.getAmount());
            assetRepository.saveAndFlush(assetEntityOpt);
        });
        log.info("Asset has just been updated!");
        return assetMapper.toDto(assetEntity.get());
    }

    public List<AssetDto> getAllAssetsByCategory(AssetCategory assetCategory) {
        List<AssetEntity> byAssetCategory = assetRepository.findByAssetCategory(assetCategory);
        return byAssetCategory.stream()
                .map(assetMapper::toDto)
                .toList();
    }

    public void deleteAssetsByUser(UserEntity userEntity) {
        assetRepository.deleteByUserEntity(userEntity);
    }

    public List<AssetDto> getAssetsByFilteredConditions(Map<String, String> conditions) {
        UserEntity loggedUserEntity = userLoginService.getLoggedUserEntity();
        FilterSpecificationEnum filterSpecification = FilterSpecificationEnum.ASSET_APPLICABLE;
        List<AssetEntity> allFilteredData = filterSpecificRepository.chooseFilterAccordingToSpecification(loggedUserEntity, conditions, filterSpecification);

        return allFilteredData.stream()
                .map(assetMapper::toDto)
                .toList();
    }

    private UserEntity getUserEntity() {
        log.info("Fetching logged entity User");
        return userLoginService.getLoggedUserEntity();
    }

}
