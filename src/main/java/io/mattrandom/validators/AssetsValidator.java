package io.mattrandom.validators;

import io.mattrandom.enums.AssetValidatorEnum;
import io.mattrandom.exceptions.AssertIncorrectException;
import io.mattrandom.services.dtos.AssetDto;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AssetsValidator {

    public void validate(AssetDto assetDto) {

        if (Objects.isNull(assetDto.getAmount())) {
            throw new AssertIncorrectException(AssetValidatorEnum.ASSETS_AMOUNT_NOT_SPECIFIED.getReason());
        }
    }
}