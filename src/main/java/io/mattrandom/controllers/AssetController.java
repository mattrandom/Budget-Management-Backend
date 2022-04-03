package io.mattrandom.controllers;

import io.mattrandom.enums.AssetCategory;
import io.mattrandom.services.AssetService;
import io.mattrandom.services.dtos.AssetDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Api(value = "REST API Asset CRUD Operations")
@RequiredArgsConstructor
@RestController
@RequestMapping("/assets")
public class AssetController {

    private final AssetService assetService;

    @GetMapping
    @ApiOperation("Fetch all Asset from DB")
    public ResponseEntity<List<AssetDto>> getAllAssets() {
        return ResponseEntity.status(HttpStatus.OK).body(assetService.getAllAssetsByPrincipal());
    }

    @GetMapping("/find")
    @ApiOperation("Fetch all Asset by particular category from DB")
    public ResponseEntity<List<AssetDto>> getAllAssetsByCategory(@RequestParam("category") String assetCategory) {
        return ResponseEntity.status(HttpStatus.OK).body(assetService.getAllAssetsByCategory(AssetCategory.valueOf(assetCategory.toUpperCase())));
    }

    @GetMapping("/filter")
    @ApiOperation("Fetch all Asset filtered by query params from DB")
    public ResponseEntity<List<AssetDto>> getAssetsByFilteredConditions(@RequestParam Map<String, String> conditions) {
        return ResponseEntity.status(HttpStatus.OK).body(assetService.getAssetsByFilteredConditions(conditions));
    }

    @PostMapping
    @ApiOperation("Save Asset")
    public ResponseEntity<AssetDto> addAssets(@RequestBody @Valid AssetDto assetDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assetService.addAsset(assetDto));
    }

    @DeleteMapping
    @ApiOperation("Delete Asset")
    public ResponseEntity<Void> deleteAsset(@RequestBody @Valid AssetDto assetDto) {
        assetService.deleteAsset(assetDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping
    @ApiOperation("Update Asset")
    public ResponseEntity<AssetDto> updateAsset(@RequestBody @Valid AssetDto assetDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assetService.updateAsset(assetDto));
    }
}
