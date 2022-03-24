package io.mattrandom.validators.filters;

import io.mattrandom.repositories.AssetRepository;
import io.mattrandom.repositories.entities.AssetEntity;
import io.mattrandom.repositories.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AssetFilterSpecificRepository extends FilterSpecificRepositoryAbstract<AssetEntity> {

    private final AssetRepository assetRepository;

    @Override
    protected List<AssetEntity> getResultsFromProperRepositoryByDateBetween(UserEntity user, LocalDateTime dateFom, LocalDateTime dateTo) {
        return assetRepository.findByIncomeDateBetween(user, dateFom, dateTo);
    }

    @Override
    protected String getFilterName() {
        return this.getClass().getSimpleName().replace("FilterSpecificRepository", "");
    }
}
