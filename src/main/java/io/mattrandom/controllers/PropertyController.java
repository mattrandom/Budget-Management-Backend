package io.mattrandom.controllers;

import io.mattrandom.services.PropertyService;
import io.mattrandom.services.dtos.PropertyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/properties")
public class PropertyController {

    private final PropertyService propertyService;

    @PostMapping
    public ResponseEntity<PropertyDto> addProperty(@RequestBody PropertyDto propertyDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.addProperty(propertyDto));
    }

    @GetMapping
    public ResponseEntity<List<PropertyDto>> getAllProperties() {
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.getAllProperties());
    }

    @PutMapping
    public ResponseEntity<PropertyDto> updateProperty(@RequestBody PropertyDto propertyDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.updateProperty(propertyDto));
    }
}
