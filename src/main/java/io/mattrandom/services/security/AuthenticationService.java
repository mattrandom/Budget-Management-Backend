package io.mattrandom.services.security;

import io.mattrandom.services.security.dtos.AuthJwtDto;
import io.mattrandom.services.security.dtos.AuthUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public AuthJwtDto generateAuthToken(AuthUserDto authUserDto) {
        UserDetails fetchedUserDetails = userDetailsService.loadUserByUsername(authUserDto.getUsername());
        String generatedJwt = jwtService.generateAccessJWT(fetchedUserDetails);

        return new AuthJwtDto(generatedJwt);
    }
}
