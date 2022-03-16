package io.mattrandom.controllers;

import io.mattrandom.services.security.AuthenticationService;
import io.mattrandom.services.security.dtos.AuthenticationJwtDto;
import io.mattrandom.services.security.dtos.AuthenticationUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @GetMapping
    public ResponseEntity<AuthenticationJwtDto> getAuthToken(@RequestBody AuthenticationUserDto authenticationUserDto) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.generateAuthToken(authenticationUserDto));
    }
}
