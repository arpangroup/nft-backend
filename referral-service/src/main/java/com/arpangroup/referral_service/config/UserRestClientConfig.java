package com.arpangroup.referral_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.net.URI;

@Configuration
public class UserRestClientConfig {
    @Bean
    public RestClient userRestClient(@Value("${user.service.base-url}") String baseUrl) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .requestInterceptor((request, body, execution) -> {
                    URI uri = request.getURI();
                    System.out.println("Full Request URL: " + uri);
                    System.out.println("HTTP Method: " + request.getMethod());
                    System.out.println("Headers: " + request.getHeaders());
                    return execution.execute(request, body);
                })
                .build();
    }
}
