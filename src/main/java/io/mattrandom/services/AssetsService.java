package io.mattrandom.services;

import io.mattrandom.dtos.AssetsDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AssetsService {

    private AssetsDto assetsDto = new AssetsDto();

    public AssetsDto getAllAssets() {
       return assetsDto;
    }

    public void addAsset(Integer assetNumber) {
        List<Integer> allAssets = assetsDto.getAssetsIds();
        if (allAssets == null) {
            allAssets = new ArrayList<>();
        }
        allAssets.add(assetNumber);
        assetsDto.setAssetsIds(allAssets);
    }
}
