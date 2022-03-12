package io.mattrandom.controllers;

import io.mattrandom.services.AssetsService;
import io.mattrandom.services.dtos.AssetDto;
import io.mattrandom.services.dtos.AssetsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/assets")
public class AssetsController {

    private final AssetsService assetsService;

    @GetMapping
    public AssetsDto getAllAssets() {
        return assetsService.getAllAssets();
    }

    @PostMapping
    public void addAssets(@RequestBody AssetDto assetDto) {
        assetsService.addAsset(assetDto);
    }

    @DeleteMapping
    public void deleteAsset(@RequestBody AssetDto assetDto) {
        assetsService.deleteAsset(assetDto);
    }
}
