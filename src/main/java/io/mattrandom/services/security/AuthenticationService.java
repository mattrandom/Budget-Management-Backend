package io.mattrandom.services.security;

import io.mattrandom.exceptions.AppUserInvalidCredentialsException;
import io.mattrandom.services.security.dtos.AuthenticationJwtDto;
import io.mattrandom.services.security.dtos.AuthenticationUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationJwtDto generateAuthToken(AuthenticationUserDto authenticationUserDto) {
        attemptAuthentication(authenticationUserDto);

        UserDetails fetchedUserDetails = userDetailsService.loadUserByUsername(authenticationUserDto.getUsername());
        String generatedJwt = jwtService.generateAccessJwt(fetchedUserDetails);

        return new AuthenticationJwtDto(generatedJwt);
    }

    private void attemptAuthentication(AuthenticationUserDto authenticationUserDto) {
        try {
            Authentication usernamePassAuthToken = new UsernamePasswordAuthenticationToken(authenticationUserDto.getUsername(), authenticationUserDto.getPassword());
            authenticationManager.authenticate(usernamePassAuthToken);
        } catch (BadCredentialsException | InternalAuthenticationServiceException e) {
            throw new AppUserInvalidCredentialsException();
        }
    }
}
