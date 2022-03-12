package io.mattrandom.services;

import io.mattrandom.mappers.AssetsMapper;
import io.mattrandom.repositories.AssetsRepository;
import io.mattrandom.repositories.entities.AssetEntity;
import io.mattrandom.services.dtos.AssetDto;
import io.mattrandom.services.dtos.AssetsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class AssetsServiceTest {

    @Mock
    private AssetsRepository assetsRepositoryMock;

    private final AssetsMapper assetsMapper = new AssetsMapper();

    private AssetsService assetsService;

    @BeforeEach
    public void init() {
        assetsService = new AssetsService(assetsRepositoryMock, assetsMapper);
    }


    @Test
    void givenAssetEntity_whenFetchAllAssets_thenReturnListWithSingleAsset() {
        //given
        int asset = 1;
        AssetEntity assetEntity = new AssetEntity(1L, BigDecimal.valueOf(asset));
        given(assetsRepositoryMock.findAll()).willReturn(List.of(assetEntity));

        //when
        AssetsDto result = assetsService.getAllAssets();

        //then
        List<Integer> assets = result.getAssetsIds();
        assertThat(assets).hasSize(1);
        assertThat(assets).containsExactly(asset);
    }

    @Test
    void givenTwoAssetsEntities_whenFetchAllAssets_thenReturnListWithTwoAssets() {
        //given
        int asset1 = 1;
        int asset2 = 10;
        AssetEntity assetEntity = new AssetEntity(1L, BigDecimal.valueOf(asset1));
        AssetEntity assetEntity2 = new AssetEntity(2L, BigDecimal.valueOf(asset2));
        given(assetsRepositoryMock.findAll()).willReturn(List.of(assetEntity, assetEntity2));

        //when
        AssetsDto result = assetsService.getAllAssets();

        //then
        List<Integer> assets = result.getAssetsIds();
        assertThat(assets).hasSize(2);
        assertThat(assets).containsExactly(asset1, asset2);
    }
    @Test
    void givenAssetEntity_whenAddAsset_thenSaveMethodShouldBeInvokedExactlyOneTime() {
        //given
        BigDecimal asset = BigDecimal.ONE;
        AssetEntity assetEntity = AssetEntity.builder()
                .amount(asset)
                .build();

        AssetDto assetDto = AssetDto.builder()
                .amount(asset)
                .build();

        //when
        assetsService.addAsset(assetDto);

        //then
        then(assetsRepositoryMock).should(times(1)).save(assetEntity);
    }
}