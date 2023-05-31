package com.wtongze.lotteryapi;

import com.wtongze.lotteryapi.repo.Fantasy5;
import com.wtongze.lotteryapi.repo.Fantasy5Repo;
import io.r2dbc.spi.ConnectionFactory;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import java.time.LocalDateTime;
import java.util.List;

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

    @Bean
    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));

        return initializer;
    }

    @Bean
    public CommandLineRunner test(Fantasy5Repo repo) {
        return args -> {
            var t = new Fantasy5();
            t.setDrawDate(LocalDateTime.now());
            t.setWinningNumbers(List.of("1", "2", "4"));
            repo.save(t).block();
        };
    }
}
