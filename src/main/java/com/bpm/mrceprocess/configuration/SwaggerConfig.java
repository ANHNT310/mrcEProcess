package com.bpm.mrceprocess.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String BEARER_KEY = "bearer-key";

    @Value("${api.info.title}")
    private String apiTitle;

    @Value("${api.info.version}")
    private String apiVersion;

    @Value("${api.info.description}")
    private String apiDescription;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .components(securityComponents())
                .addSecurityItem(new SecurityRequirement().addList(BEARER_KEY));
    }

    private Info apiInfo() {
        return new Info()
                .title(apiTitle)
                .version(apiVersion)
                .description(apiDescription);
    }

    private Components securityComponents() {
        return new Components()
                .addSecuritySchemes(BEARER_KEY,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT"));
    }
}