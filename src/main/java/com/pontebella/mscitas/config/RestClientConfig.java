package com.pontebella.mscitas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    
    @Value("${app.ms-usuarios.base-url}")
    private String usuariosBaseUrl;

    @Bean
    public RestClient usuariosRestClient() {
        return RestClient.builder()
                .baseUrl(usuariosBaseUrl)
                .build();
    }
}
