package com.hotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "Hotel Reservation System API",
        version = "1.0",
        description = "AI-Powered Hotel Reservation System with Dynamic Pricing and Sentiment Analysis"
    )
)
public class HotelReservationApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelReservationApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
} 