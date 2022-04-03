package io.mattrandom.controllers;

import io.mattrandom.services.RentalService;
import io.mattrandom.services.dtos.RentDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "REST API Rental CRUD Operations")
@RestController
@RequiredArgsConstructor
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService rentalService;

    @PostMapping("/{roomId}/rooms")
    @ApiOperation("Assign Rent to particular Room")
    public ResponseEntity<RentDto> addRoomToRentals(@PathVariable Long roomId, @RequestBody @Valid RentDto rentDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rentalService.addRoomToRental(roomId, rentDto));
    }

    @GetMapping
    @ApiOperation("Get all Rentals")
    public ResponseEntity<List<RentDto>> getRentals() {
        return ResponseEntity.status(HttpStatus.OK).body(rentalService.getAllRentals());
    }

    @DeleteMapping("/{roomId}/rooms/{rentId}")
    @ApiOperation("Remove particular Rent from particular Room")
    public ResponseEntity<RentDto> removeRoomFromRentals(@PathVariable Long roomId, @PathVariable Long rentId) {
        return ResponseEntity.status(HttpStatus.OK).body(rentalService.removeRoomFromRental(roomId, rentId));
    }
}
