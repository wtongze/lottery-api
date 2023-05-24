package com.wtongze.lotteryapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Tag(name = "Authentication")
@RestController
public class AuthenticationController {
    private final ReactiveUserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    private final JsonWebTokenUtils jwtUtils;

    @Autowired
    public AuthenticationController(
            ReactiveUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder,
            JsonWebTokenUtils jwtUtils
    ) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping(
            value = "/auth",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    @Operation(
            summary = "Authenticate User",
            description = "Authenticate user and generate JSON Web Token"
    )
    public Mono<ResponseEntity<AuthenticationResponse>> login(
            @ModelAttribute @Valid AuthenticationRequest authReq,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return userDetailsService.findByUsername(authReq.getUsername()).flatMap(user -> {
            if (passwordEncoder.encode(authReq.getPassword()).equals(user.getPassword()) &&
                    user.isAccountNonExpired() &&
                    user.isAccountNonLocked() &&
                    user.isCredentialsNonExpired()) {
                String token = jwtUtils.createToken(user);
                return Mono.just(ResponseEntity.ok(new AuthenticationResponse(token)));
            } else {
                return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
            }
        });
    }
}
