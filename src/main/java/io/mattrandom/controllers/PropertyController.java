package io.mattrandom.controllers;

import io.mattrandom.services.PropertyService;
import io.mattrandom.services.dtos.PropertyDto;
import io.mattrandom.services.dtos.PropertyExtendedDto;
import io.mattrandom.services.dtos.RoomDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "REST API Property CRUD Operations")
@RestController
@RequiredArgsConstructor
@RequestMapping("/properties")
public class PropertyController {

    private final PropertyService propertyService;

    @PostMapping
    @ApiOperation("Save Property")
    public ResponseEntity<PropertyDto> addProperty(@RequestBody @Valid PropertyDto propertyDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.addProperty(propertyDto));
    }

    @PostMapping("/{id}")
    @ApiOperation("Set Property as Sold")
    public ResponseEntity<Void> setPropertyAsSold(@PathVariable Long id) {
        propertyService.setPropertyAsSold(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/all")
    @ApiOperation("Fetch all Properties")
    public ResponseEntity<List<PropertyDto>> getAllProperties() {
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.getAllProperties());
    }

    @GetMapping("/sold/rooms")
    @ApiOperation("Fetch all sold Properties along with Rooms")
    public ResponseEntity<List<PropertyExtendedDto>> getAllSoldPropertiesWithRooms() {
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.getAllPropertiesWithRooms(true));
    }

    @GetMapping("/available/rooms")
    @ApiOperation("Fetch all available Properties along with Rooms")
    public ResponseEntity<List<PropertyExtendedDto>> getAllAvailablePropertiesWithRooms() {
        return ResponseEntity.status(HttpStatus.OK).body(propertyService.getAllPropertiesWithRooms(false));
    }

    @PutMapping
    @ApiOperation("Update Property")
    public ResponseEntity<PropertyDto> updateProperty(@RequestBody @Valid PropertyDto propertyDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.updateProperty(propertyDto));
    }

    @DeleteMapping
    @ApiOperation("Delete Property")
    public ResponseEntity<Void> deleteProperty(@RequestBody @Valid PropertyDto propertyDto) {
        propertyService.deleteProperty(propertyDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{id}/rooms/add")
    @ApiOperation("Assign Room to particular Property")
    public ResponseEntity<PropertyExtendedDto> addRoomToProperty(@PathVariable Long id, @RequestBody @Valid RoomDto roomDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.addRoomToProperty(id, roomDto));
    }

    @DeleteMapping("/{propertyId}/rooms/{roomId}")
    @ApiOperation("Remove Room from particular Property")
    public ResponseEntity<Void> deleteRoomFromProperty(@PathVariable Long propertyId, @PathVariable Long roomId) {
        propertyService.deleteRoomFromProperty(propertyId, roomId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
