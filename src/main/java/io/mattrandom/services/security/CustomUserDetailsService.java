package io.mattrandom.services.security;

import io.mattrandom.mappers.UserMapper;
import io.mattrandom.repositories.UserRepository;
import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.services.security.dtos.AuthenticationUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Looking for User with username=" + username);
        return new CustomUserDetails(userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found in database")));
    }

    public Long saveUser(AuthenticationUserDto authenticationUserDto) {
        UserEntity userEntity = userMapper.toEntity(authenticationUserDto);
        userRepository.save(userEntity);
        log.info("User saved = " + userEntity);
        return userEntity.getId();
    }
}
