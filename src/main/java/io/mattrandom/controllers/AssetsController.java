package io.mattrandom.controllers;

import io.mattrandom.services.AssetsService;
import io.mattrandom.services.dtos.AssetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/assets")
public class AssetsController {

    private final AssetsService assetsService;

    @GetMapping
    public List<AssetDto> getAllAssets() {
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

    @PutMapping
    public void updateAsset(@RequestBody AssetDto assetDto) {
        assetsService.updateAsset(assetDto);
    }
}
