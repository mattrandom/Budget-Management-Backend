package io.mattrandom.mappers;

import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.services.security.dtos.AuthenticationUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserEntity toEntity(AuthenticationUserDto authenticationUserDto) {
        if (Objects.isNull(authenticationUserDto)) {
            return null;
        }

        UserEntity.UserEntityBuilder userEntityBuilder = UserEntity.builder();

        if (Objects.nonNull(authenticationUserDto.getUsername())) {
            userEntityBuilder.username(authenticationUserDto.getUsername());
        }
        if (Objects.nonNull(authenticationUserDto.getPassword())) {
            userEntityBuilder.password(passwordEncoder.encode(authenticationUserDto.getPassword()));
        }

        return userEntityBuilder.build();
    }
}
