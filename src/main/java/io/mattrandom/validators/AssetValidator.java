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
    }
}