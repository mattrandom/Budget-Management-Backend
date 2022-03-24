package io.mattrandom.validators;

import io.mattrandom.enums.QueryParamMessageEnum;
import io.mattrandom.exceptions.AssetFilterQueryParamException;
import org.springframework.stereotype.Component;

@Component
public class AssetQueryParamFilterValidator extends QueryParamFilterValidator {

    @Override
    public void throwProperException(String missingQueryParamKey, String errorCode) {

        throw new AssetFilterQueryParamException(
                QueryParamMessageEnum.NO_ASSET_FILTER_PARAM_KEY.getMessage(missingQueryParamKey),
                errorCode
        );
    }
}
