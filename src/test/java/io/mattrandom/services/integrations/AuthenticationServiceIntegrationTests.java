package io.mattrandom.services.integrations;

import io.mattrandom.enums.AuthenticationEnum;
import io.mattrandom.exceptions.AppUserInvalidCredentialsException;
import io.mattrandom.services.security.AuthenticationService;
import io.mattrandom.services.security.CustomUserDetailsService;
import io.mattrandom.services.security.JwtService;
import io.mattrandom.services.security.dtos.AuthenticationUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
class AuthenticationServiceIntegrationTests {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;

    private AuthenticationService authenticationService;

    @BeforeEach
    void beforeEach() {
        authenticationService = new AuthenticationService(jwtService, customUserDetailsService, authenticationManager);
    }

    @Test
    void givenWrongUsername_whenAuthenticatingCredentials_thenThrowException() {
        //given

        getValidUser();

        AuthenticationUserDto wrongUsernameAuthentication = new AuthenticationUserDto();
        wrongUsernameAuthentication.setUsername("bad_credential");
        wrongUsernameAuthentication.setPassword("pass");

        //when
        Exception result = assertThrows(AppUserInvalidCredentialsException.class, () -> authenticationService.generateAuthToken(wrongUsernameAuthentication));

        //then
        assertThat(result.getMessage()).isEqualTo(AuthenticationEnum.USER_INVALID_CREDENTIALS.getMessage());
    }

    @Test
    void givenWrongPassword_whenAuthenticatingCredentials_thenThrowException() {
        //given

        getValidUser();

        AuthenticationUserDto wrongUsernameAuthentication = new AuthenticationUserDto();
        wrongUsernameAuthentication.setUsername("principal");
        wrongUsernameAuthentication.setPassword("bad_credential");

        //when
        Exception result = assertThrows(AppUserInvalidCredentialsException.class, () -> authenticationService.generateAuthToken(wrongUsernameAuthentication));

        //then
        assertThat(result.getMessage()).isEqualTo(AuthenticationEnum.USER_INVALID_CREDENTIALS.getMessage());
    }

    private void getValidUser() {
        AuthenticationUserDto authenticationUserDto = new AuthenticationUserDto();
        authenticationUserDto.setUsername("principal");
        authenticationUserDto.setPassword("pass");

        customUserDetailsService.saveUser(authenticationUserDto);

    }


}