package com.wtongze.lotteryapi;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthenticationRequest {
    @Schema(example = "user")
    @NotNull
    private String username;

    @Schema(example = "password")
    @NotNull
    private String password;
}
