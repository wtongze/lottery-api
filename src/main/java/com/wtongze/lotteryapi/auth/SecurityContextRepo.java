package com.wtongze.lotteryapi.auth;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class SecurityContextRepo implements ServerSecurityContextRepository {

    private final ReactiveAuthenticationManager authenticationManager;

    public SecurityContextRepo(ReactiveAuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Mono<Void> save(ServerWebExchange swe, SecurityContext sc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange swe) {
        String authHeader = swe.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String authToken = authHeader.substring(7);
            Authentication auth = new JwtAuthenticationToken(authToken);
            Mono<Authentication> nextAuth = Mono.just(auth).flatMap(authenticationManager::authenticate)
                    .onErrorResume(e -> Mono.empty());
            return nextAuth.map(SecurityContextImpl::new);
        }
        return Mono.empty();
    }
}
