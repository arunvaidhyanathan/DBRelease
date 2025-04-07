package com.db.release.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    /**
     * Configure the primary DataSource using properties from application.yml
     * 
     * @return Configured DataSource
     */
    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.postgresql")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
}