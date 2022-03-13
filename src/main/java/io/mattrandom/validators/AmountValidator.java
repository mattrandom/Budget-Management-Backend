package io.mattrandom.validators;

import io.mattrandom.enums.AssetValidatorEnum;
import io.mattrandom.services.dtos.AssetDto;

import java.util.Objects;

class AmountValidator implements Validator {

    private final Validator nextValidatorInChain = new IncomeDateValidator();

    @Override
    public MessageValidationGenerator messageChain(AssetDto assetDto, MessageValidationGenerator messageValidationGenerator) {
        if (Objects.isNull(assetDto.getAmount())) {
            messageValidationGenerator.addErrorMessage(AssetValidatorEnum.ASSETS_AMOUNT_NOT_SPECIFIED.getReason());
            messageValidationGenerator.addErrorCode("7B8720B358424E25A80BA2F38A6DA323");
        }

        return nextValidatorInChain.messageChain(assetDto, messageValidationGenerator);
    }
}
