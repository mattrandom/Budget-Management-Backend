package io.mattrandom.controllers;

import io.mattrandom.dtos.AssetsDto;
import io.mattrandom.services.AssetsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/assets")
public class AssetsController {

    private final AssetsService assetsService;

    @GetMapping
    public AssetsDto getAllAssets() {
        return assetsService.getAllAssets();
    }
}
