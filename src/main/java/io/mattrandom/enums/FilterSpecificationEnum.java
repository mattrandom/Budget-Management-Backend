package io.mattrandom.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FilterSpecificationEnum {

    ASSET_APPLICABLE("assetQueryParamFilterValidatorBean", "assetFilterSpecificRepositoryBean"),
    EXPENSE_APPLICABLE("expenseQueryParamFilterValidatorBean", "expenseFilterSpecificRepositoryBean");

    private final String queryParamFilterValidatorDedicatedFor;
    private final String specificRepositoryFilterDedicatedFor;
}
