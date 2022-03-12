package io.mattrandom.services;

import io.mattrandom.mappers.AssetsMapper;
import io.mattrandom.repositories.AssetsRepository;
import io.mattrandom.repositories.entities.AssetEntity;
import io.mattrandom.services.dtos.AssetsDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AssetsServiceTest {

    @Mock
    private AssetsMapper assetsMapper;

    @Mock
    private AssetsRepository assetsRepositoryMock;

    @InjectMocks
    private AssetsService assetsService;


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
}