package io.mattrandom.configurations.security.filters;

import io.mattrandom.services.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static io.mattrandom.enums.SecurityEnumConstants.JWT_PREFIX;

@Service
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith(JWT_PREFIX.getConstant())) {
            String jwt = authorizationHeader.replace(JWT_PREFIX.getConstant(), "");
            String usernameFromJwt = jwtService.getUsernameFromJwt(jwt);

            UserDetails fetchedUserDetails = userDetailsService.loadUserByUsername(usernameFromJwt);

            if (jwtService.isJwtValid(jwt, fetchedUserDetails)) {
                var auth = new UsernamePasswordAuthenticationToken(fetchedUserDetails, null, fetchedUserDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}
