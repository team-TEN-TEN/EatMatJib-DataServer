package com.tenten.eatmatjib.data.common.config;

import com.tenten.eatmatjib.data.pipeline.DataService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    private final DataService dataService;

    public AppConfig(DataService dataService) {
        this.dataService = dataService;
    }

    @Bean
    public CommandLineRunner runAtStartup() {
        return args -> {
            dataService.initData(); // 애플리케이션이 시작될 때 실행될 코드
        };
    }
}