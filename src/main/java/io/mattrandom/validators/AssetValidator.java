package io.mattrandom.validators;

import io.mattrandom.exceptions.AssetIncorrectException;
import io.mattrandom.services.dtos.AssetDto;
import org.springframework.stereotype.Component;

@Component
public class AssetValidator {

    private final Validator firstValidatorInChain = new AmountValidator();

    public void validate(AssetDto assetDto) {
        MessageValidationGenerator messageValidationGenerator = firstValidatorInChain.messageChain(assetDto, new MessageValidationGenerator());

        if (messageValidationGenerator.getErrorMessages().size() > 0 || messageValidationGenerator.getErrorCodes().size() >0) {
            throw new AssetIncorrectException(
                    String.join("; ", messageValidationGenerator.getErrorMessages()),
                    String.join("; ", messageValidationGenerator.getErrorCodes())
            );
        }
    }
}