package io.mattrandom.validators;

import io.mattrandom.enums.AssetValidatorEnum;
import io.mattrandom.exceptions.AssetIncorrectException;
import io.mattrandom.services.dtos.AssetDto;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AssetValidator {

    public void validate(AssetDto assetDto) {

        if (Objects.isNull(assetDto.getAmount())) {
            throw new AssetIncorrectException(AssetValidatorEnum.ASSETS_AMOUNT_NOT_SPECIFIED.getReason(), "7B8720B358424E25A80BA2F38A6DA323");
        }

        if (Objects.isNull(assetDto.getIncomeDate())) {
            throw new AssetIncorrectException(AssetValidatorEnum.ASSETS_INCOME_DATE_NOT_SPECIFIED.getReason(), "ecb409de-7af9-4926-b84b-1ee2a9a8dcf9");
        }
    }
}