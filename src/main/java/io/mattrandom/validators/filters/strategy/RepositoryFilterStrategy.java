package io.mattrandom.validators.filters.strategy;

import io.mattrandom.enums.FilterSpecificationEnum;
import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.validators.filters.factory.abstraction.AbstractFilterSpecificRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RepositoryFilterStrategy<T> {

    private final Map<String, AbstractFilterSpecificRepository<T>> filterStrategyMap;

    public List<T> chooseFilterAccordingToSpecification(UserEntity user, Map<String, String> conditions, FilterSpecificationEnum filterSpecification) {
        return filterStrategyMap.get(filterSpecification.getSpecificRepositoryFilterDedicatedFor()).getAllFilteredData(user, conditions, filterSpecification);
    }
}
