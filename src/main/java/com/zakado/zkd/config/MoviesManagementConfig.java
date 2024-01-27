package com.zakado.zkd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MoviesManagementConfig {
    @Bean
    public RestTemplate template() {
        return new RestTemplate();
    }
}
