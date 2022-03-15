package io.mattrandom.services.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.mattrandom.constants.SecurityConstants;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    public String generateAccessJWT(UserDetails userDetails) {
        long durationOfDayInMillis = 24 * 60 * 60 * 1000;
        Algorithm algorithmSHA256 = Algorithm.HMAC256(SecurityConstants.JWT_SECRET.getConstant().getBytes());

        return JWT.create()
                .withIssuer(userDetails.getUsername())
                .withSubject(userDetails.getUsername())
                .withClaim("user", userDetails.getUsername())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + durationOfDayInMillis))
                .sign(algorithmSHA256);
    }
}
