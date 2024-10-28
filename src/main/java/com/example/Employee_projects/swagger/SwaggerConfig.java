package com.example.Employee_projects.swagger;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        SecurityScheme jwtScheme = new SecurityScheme()
                .type(Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("JWT Token")
                .description("Enter JWT Bearer token **_only_** after  `Bearer ` prefix")
                .in(SecurityScheme.In.HEADER);

        return new OpenAPI()
                .info(new Info()
                        .title("Employee and Project Management API")
                        .version("1.0")
                        .description("API documentation for Employee and Project management system"))
                .addSecurityItem(new SecurityRequirement().addList("JWT Token"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("JWT Token", jwtScheme));
    }
}
