package io.mattrandom.controllers;

import io.mattrandom.services.RoomService;
import io.mattrandom.services.dtos.RoomDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "REST API Room CRUD Operations")
@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    @ApiOperation("Save or Update Room")
    public ResponseEntity<RoomDto> addRoom(@RequestBody @Valid RoomDto roomDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.saveOrUpdateRoom(roomDto));
    }

    @GetMapping
    @ApiOperation("Fetch all Rooms")
    public ResponseEntity<List<RoomDto>> getAllRooms() {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.getAllRooms());
    }

    @PostMapping("/{id}")
    @ApiOperation("Inactivate Room by setting price as 0")
    public ResponseEntity<RoomDto> inactivateRoom(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.inactivateRoom(id));
    }
}
