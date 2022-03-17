package io.mattrandom.services.integrations;

import io.mattrandom.enums.AuthenticationEnum;
import io.mattrandom.exceptions.AppUserAlreadyExistException;
import io.mattrandom.exceptions.AppUserNotFoundException;
import io.mattrandom.repositories.UserRepository;
import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.services.security.CustomUserDetailsService;
import io.mattrandom.services.security.dtos.AuthenticationUserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
class CustomUserDetailsServiceIntegrationTests {

    private static final String USER_LOGIN = "login";
    private static final String USER_PASSWORD = "pass";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Test
    @DisplayName("Should return from DB UserDetails with given credentials")
    void givenUserEntity_whenCallingLoadByUsername_thenReturnUserDetails() {
        //given
        initDB();

        //when
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(USER_LOGIN);

        //then
        assertThat(userDetails.getUsername()).isEqualTo(USER_LOGIN);
        assertThat(userDetails.getPassword()).isEqualTo(USER_PASSWORD);
    }

    @Test
    @DisplayName("Should save UserEntity into DB")
    void givenUserDtoObject_whenSavingUser_thenCredentialsShouldHaveProperValues() {
        //given
        AuthenticationUserDto dto = new AuthenticationUserDto();
        dto.setUsername(USER_LOGIN);
        dto.setPassword(USER_PASSWORD);

        //when
        Long userDetailsID = customUserDetailsService.saveUser(dto);
        UserEntity userEntity = userRepository.findById(userDetailsID).get();

        //then
        assertThat(userDetailsID).isEqualTo(userEntity.getId());

        assertAll(
                () -> assertThat(userEntity.getUsername()).isEqualTo(USER_LOGIN),
                () -> assertThat(userEntity.getPassword()).contains(hashPasswordAndGetCommonSubstring()),
                () -> assertThat(userEntity.getPassword()).matches(checkRegexPattern())
        );
    }

    @Test
    @DisplayName("An exception should be thrown when particular User is not found in DB")
    void givenWrongUsername_whenCallingLoadByUsername_thenShouldThrowException() {
        //given
        initDB();

        //when
        AppUserNotFoundException notFound = assertThrows(AppUserNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername("wrong_login"));

        //then
        assertThat(notFound.getMessage()).isEqualTo(AuthenticationEnum.USER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("An exception should be thrown when attempting to save User being already existed in DB")
    void givenExistedUser_whenCallingSaveUser_thenShouldThrowException() {
        //given
        initDB();
        AuthenticationUserDto dto = new AuthenticationUserDto();
        dto.setUsername(USER_LOGIN);
        dto.setPassword(USER_PASSWORD);

        //when
        AppUserAlreadyExistException alreadyExist = assertThrows(AppUserAlreadyExistException.class,
                () -> customUserDetailsService.saveUser(dto));

        //then
        assertThat(alreadyExist.getMessage()).isEqualTo(AuthenticationEnum.USER_ALREADY_EXIST.getMessage());
    }

    private void initDB() {
        UserEntity user = UserEntity.builder()
                .username(USER_LOGIN)
                .password(USER_PASSWORD)
                .build();

        userRepository.save(user);
    }

    private String hashPasswordAndGetCommonSubstring() {
        return BCrypt.hashpw(USER_PASSWORD, BCrypt.gensalt()).substring(0, 7);
    }

    private Pattern checkRegexPattern() {
        return Pattern.compile("^[$]2[abxy]?[$](?:0[4-9]|[12][0-9]|3[01])[$][./0-9a-zA-Z]{53}$");
    }
}