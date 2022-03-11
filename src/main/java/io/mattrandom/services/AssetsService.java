package io.mattrandom.services;

import io.mattrandom.dtos.AssetsDto;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AssetsService {

    public AssetsDto getAllAssets() {
        AssetsDto assetsDto = new AssetsDto();
        assetsDto.setAssets(Arrays.asList(5, 10, 15));
        return assetsDto;
    }
}
