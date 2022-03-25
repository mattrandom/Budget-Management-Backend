package io.mattrandom.validators.filters.strategy;

import io.mattrandom.enums.FilterSpecificationEnum;
import io.mattrandom.validators.filters.factory.abstraction.AbstractQueryParamFilterValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class FilterStrategy {

    private final Map<String, AbstractQueryParamFilterValidator> filterStrategyMap;

    public void chooseFilterAccordingToSpecification(Map<String, String> conditions, FilterSpecificationEnum filterSpecification) {
        filterStrategyMap.get(filterSpecification.getQueryParamFilterValidatorDedicatedFor()).chooseFilter(conditions);
    }
}
