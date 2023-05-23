package com.wtongze.lotteryapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        servers = {
                @Server(url = "/", description = "Default Server")
        }
)
public class LotteryApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(LotteryApiApplication.class, args);
    }
}
