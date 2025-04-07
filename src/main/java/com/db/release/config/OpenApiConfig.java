package com.db.release.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for OpenAPI documentation (Swagger)
 */
@Configuration
public class OpenApiConfig {

    /**
     * Configures the OpenAPI documentation for the application
     * 
     * @return the OpenAPI configuration
     */
    @Bean
    public OpenAPI dbReleaseOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Database Release API")
                        .description("API for managing database releases and rollbacks using Liquibase")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("DB Release Team")
                                .email("support@dbrelease.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}