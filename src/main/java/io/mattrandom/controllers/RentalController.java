package io.mattrandom.controllers;

import io.mattrandom.services.RentalService;
import io.mattrandom.services.dtos.RentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService rentalService;

    @PostMapping("/{roomId}/rooms")
    public ResponseEntity<RentDto> addRoomToRentals(@PathVariable Long roomId, @RequestBody RentDto rentDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rentalService.addRoomToRental(roomId, rentDto));
    }

    @GetMapping
    public ResponseEntity<List<RentDto>> getRentals() {
        return ResponseEntity.status(HttpStatus.OK).body(rentalService.getAllRentals());
    }

    @DeleteMapping("/{roomId}/rooms/{rentId}")
    public ResponseEntity<RentDto> removeRoomFromRentals(@PathVariable Long roomId, @PathVariable Long rentId) {
        return ResponseEntity.status(HttpStatus.OK).body(rentalService.removeRoomFromRental(roomId, rentId));
    }
}
