package io.mattrandom.validators.filters.factory.implementation;

import io.mattrandom.enums.AssetCategory;
import io.mattrandom.repositories.AssetRepository;
import io.mattrandom.repositories.entities.AssetEntity;
import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.validators.filters.factory.abstraction.AbstractFilterSpecificRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Component("assetFilterSpecificRepositoryBean")
@RequiredArgsConstructor
public class AssetFilterSpecificRepository extends AbstractFilterSpecificRepository<AssetEntity> {

    private final AssetRepository assetRepository;

    @Override
    protected List<AssetEntity> getResultsFromProperRepositoryByDateBetween(UserEntity user, LocalDateTime dateFom, LocalDateTime dateTo, String category) {
        return assetRepository.findByIncomeDateBetween(user, dateFom, dateTo, mapToEnum(category));
    }

    private List<AssetCategory> mapToEnum(String category) {
        if (Objects.isNull(category)) {
            return List.of(AssetCategory.values());
        }
        return List.of(AssetCategory.valueOf(category.toUpperCase()));
    }
}
