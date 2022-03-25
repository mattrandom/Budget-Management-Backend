package io.mattrandom.validators.filters.factory.implementation;

import io.mattrandom.enums.QueryParamMessageEnum;
import io.mattrandom.exceptions.AssetFilterQueryParamException;
import io.mattrandom.validators.filters.factory.abstraction.AbstractQueryParamFilterValidator;
import org.springframework.stereotype.Component;

@Component("assetQueryParamFilterValidatorBean")
class AssetQueryParamFilterValidator extends AbstractQueryParamFilterValidator {

    @Override
    public void throwProperException(String missingQueryParamKey, String errorCode) {

        throw new AssetFilterQueryParamException(
                QueryParamMessageEnum.NO_ASSET_FILTER_PARAM_KEY.getMessage(missingQueryParamKey),
                errorCode
        );
    }
}
