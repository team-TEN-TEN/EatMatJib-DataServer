package com.tenten.eatmatjib.data;

import com.tenten.eatmatjib.data.pipeline.DataService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class EatMatJibDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(EatMatJibDataApplication.class, args);
    }

}
