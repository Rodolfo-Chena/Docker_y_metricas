package com.example.hexagonalorders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main application class for the Hexagonal Orders system.
 * This class is responsible for:
 * - Bootstrapping the Spring Boot application
 * - Enabling component scanning for the entire application
 * - Configuring the application context
 * 
 * The application follows Hexagonal Architecture principles with:
 * - Domain layer containing business logic and ports
 * - Application layer containing use cases
 * - Infrastructure layer containing adapters and external services
 */
@SpringBootApplication
@EnableScheduling
public class HexagonalOrdersApplication {
    public static void main(String[] args) {
        SpringApplication.run(HexagonalOrdersApplication.class, args);
    }
} 