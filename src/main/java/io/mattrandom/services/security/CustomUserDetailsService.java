package io.mattrandom.services.security;

import io.mattrandom.exceptions.AppUserAlreadyExistException;
import io.mattrandom.exceptions.AppUserNotFoundException;
import io.mattrandom.mappers.UserMapper;
import io.mattrandom.repositories.UserRepository;
import io.mattrandom.repositories.entities.UserEntity;
import io.mattrandom.services.AssetsService;
import io.mattrandom.services.UserLoginService;
import io.mattrandom.services.security.dtos.AuthenticationUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AssetsService assetsService;
    private final UserLoginService userLoginService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Looking for User with username=" + username);
        return new CustomUserDetails(userRepository.findByUsername(username)
                .orElseThrow(AppUserNotFoundException::new));
    }

    public Long saveUser(AuthenticationUserDto authenticationUserDto) {
        checkIfUserExits(authenticationUserDto);
        UserEntity userEntity = userMapper.toEntity(authenticationUserDto);
        userRepository.save(userEntity);
        log.info("User saved = " + userEntity);
        return userEntity.getId();
    }

    @Transactional
    public void deleteUser() {
        UserEntity loggedUserEntity = userLoginService.getLoggedUserEntity();
        assetsService.deleteAssetsByUser(loggedUserEntity);
        userRepository.delete(loggedUserEntity);
    }

    private void checkIfUserExits(AuthenticationUserDto authenticationUserDto) {
        Optional<UserEntity> findUserEntity = userRepository.findByUsername(authenticationUserDto.getUsername());
        if (findUserEntity.isPresent()) {
            throw new AppUserAlreadyExistException();
        }
    }
}
