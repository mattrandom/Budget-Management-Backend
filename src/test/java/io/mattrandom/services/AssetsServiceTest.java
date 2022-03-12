package io.mattrandom.services;

import io.mattrandom.dtos.AssetsDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AssetsServiceTest {

    @Test
    void givenAddAsset_whenFetchAllAssets_thenReturnListWithSingleAsset() {
        //given
        Integer asset = 1;
        AssetsService assetsService = new AssetsService();
        assetsService.addAsset(asset);

        //when
        AssetsDto result = assetsService.getAllAssets();

        //then
        List<Integer> assets = result.getAssetsIds();
        assertThat(assets).hasSize(1);
        assertThat(assets).containsExactly(asset);
    }

    @Test
    void givenAddTwoAssets_whenFetchAllAssets_thenReturnListWithTwoAssets() {
        //given
        Integer asset1 = 1;
        Integer asset2 = 10;
        AssetsService assetsService = new AssetsService();
        assetsService.addAsset(asset1);
        assetsService.addAsset(asset2);

        //when
        AssetsDto result = assetsService.getAllAssets();

        //then
        List<Integer> assets = result.getAssetsIds();
        assertThat(assets).hasSize(2);
        assertThat(assets).containsExactly(asset1, asset2);
    }

}