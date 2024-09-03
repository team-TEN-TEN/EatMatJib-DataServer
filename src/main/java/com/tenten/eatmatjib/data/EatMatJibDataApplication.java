package com.tenten.eatmatjib.data;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@MapperScan("com.tenten.eatmatjib.data.pipeline")
public class EatMatJibDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(EatMatJibDataApplication.class, args);
    }

}
