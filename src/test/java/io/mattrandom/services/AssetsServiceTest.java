package io.mattrandom.services;

import io.mattrandom.enums.AssetValidatorEnum;
import io.mattrandom.exceptions.AssetIncorrectException;
import io.mattrandom.mappers.AssetsMapper;
import io.mattrandom.repositories.AssetsRepository;
import io.mattrandom.repositories.entities.AssetEntity;
import io.mattrandom.services.dtos.AssetDto;
import io.mattrandom.validators.AssetValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class AssetsServiceTest {

    @Mock
    private AssetsRepository assetsRepositoryMock;

    private final AssetsMapper assetsMapper = new AssetsMapper();
    private final AssetValidator assetValidator = new AssetValidator();

    private AssetsService assetsService;


    @BeforeEach
    public void init() {
        assetsService = new AssetsService(assetsRepositoryMock, assetsMapper, assetValidator);
    }


    @Test
    void givenAssetEntity_whenFetchAllAssets_thenReturnListWithSingleAsset() {
        //given
        BigDecimal one = BigDecimal.ONE;
        AssetEntity assetEntity = AssetEntity.builder()
                .amount(one)
                .build();
        given(assetsRepositoryMock.findAll()).willReturn(List.of(assetEntity));

        //when
        List<AssetDto> assetDtoResult = assetsService.getAllAssets();

        //then
        assertThat(assetDtoResult).hasSize(1);
        assertThat(assetDtoResult.get(0).getAmount()).isEqualTo(one);
    }

    @Test
    void givenTwoAssetsEntities_whenFetchAllAssets_thenReturnListWithTwoAssets() {
        //given
        BigDecimal one = BigDecimal.ONE;
        BigDecimal ten = BigDecimal.TEN;
        AssetEntity assetEntity = AssetEntity.builder()
                .amount(one)
                .build();
        AssetEntity assetEntity2 = AssetEntity.builder()
                .amount(ten)
                .build();
        given(assetsRepositoryMock.findAll()).willReturn(List.of(assetEntity, assetEntity2));

        //when
        List<AssetDto> assetDtoResult = assetsService.getAllAssets();

        //then
        assertThat(assetDtoResult).hasSize(2);
    }
    
    @Test
    void givenAssetEntity_whenAddAsset_thenSaveMethodShouldBeInvokedExactlyOneTime() {
        //given
        BigDecimal asset = BigDecimal.ONE;
        AssetEntity assetEntity = AssetEntity.builder()
                .amount(asset)
                .incomeDate(LocalDateTime.now())
                .build();

        AssetDto assetDto = AssetDto.builder()
                .amount(asset)
                .incomeDate(LocalDateTime.now())
                .build();

        //when
        assetsService.addAsset(assetDto);

        //then
        then(assetsRepositoryMock).should(times(1)).save(assetEntity);
    }

    @Test
    void givenNullAmountValueOfAssetDto_whenAddAsset_thenShouldThrowException() {
        //given
        AssetDto assetDto = AssetDto.builder()
                .amount(null)
                .incomeDate(LocalDateTime.now())
                .build();

        //when
        var assertIncorrectException = assertThrows(AssetIncorrectException.class, () -> assetsService.addAsset(assetDto));

        //then
        assertThat(assertIncorrectException.getMessage()).isEqualTo(AssetValidatorEnum.ASSETS_AMOUNT_NOT_SPECIFIED.getReason());
    }

    @Test
    void givenNullAmountAndIncomeDateValuesOfAssetDto_whenAddAsset_thenShouldThrowException() {
        //given
        AssetDto assetDto = AssetDto.builder()
                .amount(null)
                .incomeDate(null)
                .build();

        List<String> messages = List.of(AssetValidatorEnum.ASSETS_AMOUNT_NOT_SPECIFIED.getReason(), AssetValidatorEnum.ASSETS_INCOME_DATE_NOT_SPECIFIED.getReason());

        //when
        var assertIncorrectException = assertThrows(AssetIncorrectException.class, () -> assetsService.addAsset(assetDto));

        //then
        assertThat(assertIncorrectException.getMessage()).isEqualTo(String.join("; ", messages));
    }

    @Test
    void givenAssetEntity_whenUpdateAsset_thenShouldCallSaveAndFlushMethod() {
        //given
        AssetEntity assetEntity = AssetEntity.builder()
                .amount(BigDecimal.ONE)
                .build();

        AssetDto assetDto = AssetDto.builder()
                .amount(BigDecimal.TEN)
                .build();

        given(assetsRepositoryMock.findById(any())).willReturn(Optional.of(assetEntity));

        //when
        assetsService.updateAsset(assetDto);

        //then
        then(assetsRepositoryMock).should(times(1)).saveAndFlush(assetEntity);
    }

    @Test
    void givenNullIncomeDateValueOfAssetDto_whenAddAsset_thenShouldThrowException() {
        //given
        AssetDto assetDto = AssetDto.builder()
                .amount(BigDecimal.ONE)
                .incomeDate(null)
                .build();

        //when
        var assertIncorrectException = assertThrows(AssetIncorrectException.class, () -> assetsService.addAsset(assetDto));

        //then
        assertThat(assertIncorrectException.getMessage()).isEqualTo(AssetValidatorEnum.ASSETS_INCOME_DATE_NOT_SPECIFIED.getReason());
    }
}