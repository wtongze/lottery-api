package com.wtongze.lotteryapi.auth;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {
    private final JsonWebTokenUtils utils;

    @Autowired
    public AuthenticationManager(JsonWebTokenUtils utils) {
        this.utils = utils;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        try {
            DecodedJWT token = utils.decodeToken(authToken);
            List<String> rolesList = token.getClaim("roles").asList(String.class);
            return Mono.just(new JwtAuthenticationToken(
                    authToken,
                    rolesList.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList()))
            );
        } catch (Exception e) {
            return Mono.error(new BadCredentialsException("Bad Credentials"));
        }
    }
}
