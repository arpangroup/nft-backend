package com.arpangroup.user_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

@Controller
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("OpenAPI definition")
                        .version("1.0")
                        .description("API documentation for UserServiceApplication"));
    }
}
