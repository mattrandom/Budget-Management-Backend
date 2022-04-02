package io.mattrandom.controllers;

import io.mattrandom.services.PropertyService;
import io.mattrandom.services.dtos.PropertyDto;
import io.mattrandom.services.dtos.PropertyExtendedDto;
import io.mattrandom.services.dtos.RoomDto;
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

    @PostMapping("/{id}")
    public ResponseEntity<Void> setPropertyAsSold(@PathVariable Long id) {
        propertyService.setPropertyAsSold(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<PropertyDto>> getAllPropertiesWithRooms() {
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.getAllProperties());
    }

    @GetMapping("/sold/rooms")
    public ResponseEntity<List<PropertyExtendedDto>> getAllSoldPropertiesWithRooms() {
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.getAllPropertiesWithRooms(true));
    }

    @GetMapping("/available/rooms")
    public ResponseEntity<List<PropertyExtendedDto>> getAllAvailablePropertiesWithRooms() {
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.getAllPropertiesWithRooms(false));
    }

    @PutMapping
    public ResponseEntity<PropertyDto> updateProperty(@RequestBody PropertyDto propertyDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.updateProperty(propertyDto));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProperty(@RequestBody PropertyDto propertyDto) {
        propertyService.deleteProperty(propertyDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{id}/rooms/add")
    public ResponseEntity<PropertyExtendedDto> addRoomToProperty(@PathVariable Long id, @RequestBody RoomDto roomDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.addRoomToProperty(id, roomDto));
    }

    @DeleteMapping("/{propertyId}/rooms/{roomId}")
    public ResponseEntity<Void> deleteRoomFromProperty(@PathVariable Long propertyId, @PathVariable Long roomId) {
        propertyService.deleteRoomFromProperty(propertyId, roomId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
