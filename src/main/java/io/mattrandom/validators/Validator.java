package io.mattrandom.validators;

import io.mattrandom.services.dtos.AssetDto;

public interface Validator {

   MessageValidationGenerator messageChain(AssetDto assetDto, MessageValidationGenerator messageValidationGenerator);
}
