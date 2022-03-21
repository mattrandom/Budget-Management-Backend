package io.mattrandom.controllers;

import io.mattrandom.services.security.AuthenticationService;
import io.mattrandom.services.security.CustomUserDetailsService;
import io.mattrandom.services.security.dtos.AuthenticationJwtDto;
import io.mattrandom.services.security.dtos.AuthenticationUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final CustomUserDetailsService customUserDetailsService;

    @GetMapping
    public ResponseEntity<AuthenticationJwtDto> getAuthToken(@RequestBody AuthenticationUserDto authenticationUserDto) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.generateAuthToken(authenticationUserDto));
    }

    @PostMapping
    public ResponseEntity<Long> saveUser(@RequestBody AuthenticationUserDto authenticationUserDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customUserDetailsService.saveUser(authenticationUserDto));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser() {
        customUserDetailsService.deleteUser();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}