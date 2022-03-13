package io.mattrandom.validators;

import io.mattrandom.enums.AssetValidatorEnum;
import io.mattrandom.services.dtos.AssetDto;

import java.util.Objects;

class IncomeDateValidator implements Validator {

    @Override
    public MessageValidationGenerator messageChain(AssetDto assetDto, MessageValidationGenerator messageValidationGenerator) {
        if (Objects.isNull(assetDto.getIncomeDate())) {
            messageValidationGenerator.addErrorMessage(AssetValidatorEnum.ASSETS_INCOME_DATE_NOT_SPECIFIED.getReason());
            messageValidationGenerator.addErrorCode("B8F1D584822B40B3A8D542007EF918B3");
        }

        return messageValidationGenerator;
    }
}
