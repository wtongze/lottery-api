package com.wtongze.lotteryapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @GetMapping("/auth")
    public String auth() {
        // TODO: Implement login endpoint.
        return null;
    }
}
