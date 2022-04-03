package io.mattrandom.controllers;

import io.mattrandom.services.security.AuthenticationService;
import io.mattrandom.services.security.CustomUserDetailsService;
import io.mattrandom.services.security.dtos.AuthenticationJwtDto;
import io.mattrandom.services.security.dtos.AuthenticationUserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "Save User Principal & Generate JWT")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final CustomUserDetailsService customUserDetailsService;

    @PostMapping("/token")
    @ApiOperation("Generate Auth JWT")
    public ResponseEntity<AuthenticationJwtDto> getAuthToken(@RequestBody @Valid AuthenticationUserDto authenticationUserDto) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.generateAuthToken(authenticationUserDto));
    }

    @PostMapping("/signup")
    @ApiOperation("Sign up User Principal")
    public ResponseEntity<Long> saveUser(@RequestBody @Valid AuthenticationUserDto authenticationUserDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customUserDetailsService.saveUser(authenticationUserDto));
    }

    @DeleteMapping
    @ApiOperation("Delete User Principal")
    public ResponseEntity<Void> deleteUser() {
        customUserDetailsService.deleteUser();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}