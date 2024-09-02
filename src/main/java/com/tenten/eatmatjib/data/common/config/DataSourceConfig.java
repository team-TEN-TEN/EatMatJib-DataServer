package com.tenten.eatmatjib.data.common.config;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.data")
    public DataSourceProperties eatDataDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "eatDataDataSource")
    @Primary
    public DataSource eatDataDataSource() {
        return eatDataDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.dev")
    public DataSourceProperties eatDevDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "eatDevDataSource")
    public DataSource eatDevDataSource() {
        return eatDevDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }
}

