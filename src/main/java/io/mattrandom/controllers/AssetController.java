package io.mattrandom.controllers;

import io.mattrandom.enums.AssetCategory;
import io.mattrandom.services.AssetService;
import io.mattrandom.services.dtos.AssetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/assets")
public class AssetController {

    private final AssetService assetService;

    @GetMapping
    public List<AssetDto> getAllAssets() {
        return assetService.getAllAssetsByPrincipal();
    }

    @GetMapping("/find")
    public List<AssetDto> getAllAssetsByCategory(@RequestParam("category") String assetCategory) {
        return assetService.getAllAssetsByCategory(AssetCategory.valueOf(assetCategory.toUpperCase()));
    }

    @GetMapping("/filter")
    public List<AssetDto> getAssetsByFilteredConditions(@RequestParam Map<String, String> conditions) {
        return assetService.getAssetsByFilteredConditions(conditions);
    }

    @PostMapping
    public void addAssets(@RequestBody AssetDto assetDto) {
        assetService.addAsset(assetDto);
    }

    @DeleteMapping
    public void deleteAsset(@RequestBody AssetDto assetDto) {
        assetService.deleteAsset(assetDto);
    }

    @PutMapping
    public void updateAsset(@RequestBody AssetDto assetDto) {
        assetService.updateAsset(assetDto);
    }
}
