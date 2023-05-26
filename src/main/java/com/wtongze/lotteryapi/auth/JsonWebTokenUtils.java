package com.wtongze.lotteryapi.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;

@Component
public class JsonWebTokenUtils {
    private final Algorithm algorithm;
    private final JWTVerifier verifier;

    public JsonWebTokenUtils(@Value("${jwt.secret}") String secret) {
        this.algorithm = Algorithm.HMAC256(secret);
        this.verifier =  JWT.require(algorithm).build();
    }

    public String createToken(UserDetails userDetails) {

        var roles = new ArrayList<String>();
        for (var auth : userDetails.getAuthorities()) {
            roles.add(auth.getAuthority());
        }
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withClaim("roles", roles)
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(60 * 60))
                .sign(algorithm);
    }

    public DecodedJWT decodeToken(String token) {
        return verifier.verify(token);
    }
}
