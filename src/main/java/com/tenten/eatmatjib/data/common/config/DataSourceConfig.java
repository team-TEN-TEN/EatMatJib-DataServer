package com.tenten.eatmatjib.data.common.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfig {

    @Bean
    @Primary
    @Qualifier("eatDataConfig")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.data")
    public HikariConfig eatDataHikariConfig() {
        return new HikariConfig();
    }

    @Bean
    @Primary
    @Qualifier("eatDataDataSource")
    public DataSource eatDataDataSource() {
        return new HikariDataSource(eatDataHikariConfig());
    }

    @Bean
    @Qualifier("eatDevConfig")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.dev")
    public HikariConfig eatDevHikariConfig() {
        return new HikariConfig();
    }

    @Bean
    @Qualifier("eatDevDataSource")
    public DataSource eatDevDataSource() {
        return new HikariDataSource(eatDevHikariConfig());
    }
}

