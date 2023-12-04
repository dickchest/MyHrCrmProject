package com.myhrcrmproject.configuration.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "HRMasterConnect",
                description = "Revolutionize HR processes with HRMasterConnect—a dynamic platform that seamlessly manages recruitment, onboarding, performance, and payroll. Enhance efficiency, communication, and analytics for a connected HR experience.",
                version = "1.0.0",
                contact= @Contact(
                        name = "Denys Chaykovskyy",
                        email = "dchaykovskyy@gmail.com",
                        url = "https://www.linkedin.com/in/dennis-chaykovskyy-865284288/"
        )
        )
)
public class OpenApiConfig {
}
