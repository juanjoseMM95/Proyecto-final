package com.arka.arkajjmunozm.infraestructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${microservices.cotizador.url}")
    private String cotizadorBaseUrl;

    @Bean
    public WebClient cotizadorWebClient() {
        return WebClient.builder()
                .baseUrl(cotizadorBaseUrl)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
