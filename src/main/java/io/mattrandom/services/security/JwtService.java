package io.mattrandom.services.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.mattrandom.enums.SecurityEnumConstants;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    private final long DURATION_OF_DAY_MILLIS = 24 * 60 * 60 * 1000;

    public String generateAccessJwt(UserDetails userDetails) {
        return JWT.create()
                .withIssuer("Some Issuer Value")
                .withSubject(userDetails.getUsername())
                .withClaim("user", userDetails.getUsername())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + DURATION_OF_DAY_MILLIS))
                .sign(getSHA256Algorithm());
    }

    public String getUsernameFromJwt(String jwt) {
        return getVerifiedDecodedJwt(jwt).getSubject();
    }

    public Date getJwtExpirationDate(String jwt) {
        return getVerifiedDecodedJwt(jwt).getExpiresAt();
    }

    public boolean isJwtValid(String jwt, UserDetails userDetails) {
        String usernameFromJwt = getUsernameFromJwt(jwt);
        return usernameFromJwt.equals(userDetails.getUsername()) && !isJwtExpired(jwt);
    }

    private boolean isJwtExpired(String jwt) {
        return getJwtExpirationDate(jwt).before(new Date());
    }

    private DecodedJWT getVerifiedDecodedJwt(String jwt) {
        JWTVerifier algorithmForJwtSignatureVerification = JWT.require(getSHA256Algorithm()).build();
        return algorithmForJwtSignatureVerification.verify(jwt);
    }

    private Algorithm getSHA256Algorithm() {
        return Algorithm.HMAC256(SecurityEnumConstants.JWT_SECRET.getConstant().getBytes());
    }
}
