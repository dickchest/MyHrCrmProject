package com.myhrcrmproject.configuration.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "HR CRM",
                description = "Revolutionize HR processes with HR CRM (Human Resources Customer Relationship Management) — a dynamic platform that seamlessly manages recruitment, onboarding, performance, and payroll. Enhance efficiency, communication, and analytics for a connected HR experience.",
                version = "1.0.0",
                contact = @Contact(
                        name = "Denys Chaykovskyy",
                        email = "dchaykovskyy@gmail.com",
                        url = "https://www.linkedin.com/in/dennis-chaykovskyy-865284288/"
                )
        )
)
public class OpenApiConfig {
}
