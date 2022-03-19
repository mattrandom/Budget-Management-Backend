package io.mattrandom.services;

import io.mattrandom.exceptions.AppUserNotFoundException;
import io.mattrandom.repositories.UserRepository;
import io.mattrandom.repositories.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLoginService {

    private final UserRepository userRepository;

    public UserEntity getLoggedUserEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        String username = principal.getUsername();

        return userRepository.findByUsername(username)
                .orElseThrow(AppUserNotFoundException::new);
    }
}
