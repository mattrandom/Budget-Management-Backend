package io.mattrandom.services.security;

import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.services.security.dtos.AuthenticationJwtDto;
import io.mattrandom.services.security.dtos.AuthenticationUserDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willCallRealMethod;

@Slf4j
@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private JwtService jwtServiceMock;
    @Mock
    private UserDetailsService userDetailsServiceMock;
    @Mock
    private AuthenticationManager authenticationManagerMock;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void given_when_then() {
        //given
        AuthenticationUserDto authenticationUserDto = new AuthenticationUserDto();
        authenticationUserDto.setUsername("principal");
        authenticationUserDto.setPassword("pass");

        UserEntity userEntity = UserEntity.builder().username("principal").password("pass").build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(authenticationUserDto.getUsername(), authenticationUserDto.getPassword());
        given(authenticationManagerMock.authenticate(any())).willReturn(authentication);

        UserDetails userDetails = new CustomUserDetails(userEntity);
        given(userDetailsServiceMock.loadUserByUsername(authenticationUserDto.getUsername())).willReturn(userDetails);
        willCallRealMethod().given(jwtServiceMock).generateAccessJwt(userDetails);

        //when
        AuthenticationJwtDto token = authenticationService.generateAuthToken(authenticationUserDto);
        log.debug(token.getJwt());

        //then
        assertThat(token).isNotNull();
        assertThat(token.getJwt()).isNotNull();
        assertThat(token.getJwt()).startsWith("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9");
        assertThat(token.getJwt().substring(0, 20)).contains("eyJ0eXAiOiJKV1QiLCJh");

    }
}