package com.myhrcrmproject.configuration.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("Public")
                .pathsToMatch("/public/**")
                .build();
    }

    @Bean
    public GroupedOpenApi privateApi() {
        return GroupedOpenApi.builder()
                .group("Private")
                .pathsToMatch("/private/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("HR CRM")
                        .version("v1.0.0")
                        .description("Revolutionize HR processes with HR CRM (Human Resources Customer Relationship Management) — a dynamic platform that seamlessly manages recruitment, onboarding, performance, and payroll. Enhance efficiency, communication, and analytics for a connected HR experience.")
                        .contact(
                                new Contact()
                                        .name("Denys Chaykovskyy")
                                        .email("dchaykovskyy@gmail.com")
                                        .url("https://www.linkedin.com/in/dennis-chaykovskyy-865284288/")
                        )
                )
                .components(new Components()
                        .addSecuritySchemes("Bearer",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList("Bearer"));
    }
}
