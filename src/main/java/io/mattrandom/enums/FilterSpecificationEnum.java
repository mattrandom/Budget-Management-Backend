package io.mattrandom.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FilterSpecificationEnum {

    ASSET_APPLICABLE("assetQueryParamFilterValidatorBean"),
    EXPENSE_APPLICABLE("expenseQueryParamFilterValidatorBean");

    private final String queryParamFilterValidatorDedicatedFor;
}
