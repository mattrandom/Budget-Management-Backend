package io.mattrandom.controllers;

import io.mattrandom.services.security.AuthenticationService;
import io.mattrandom.services.security.dtos.AuthJwtDto;
import io.mattrandom.services.security.dtos.AuthUserDto;
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
    public ResponseEntity<AuthJwtDto> getAuthToken(@RequestBody AuthUserDto authUserDto) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.generateAuthToken(authUserDto));
    }
}
