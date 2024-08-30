package com.tenten.eatmatjib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class EatMatJibApplication {

    public static void main(String[] args) {
        SpringApplication.run(EatMatJibApplication.class, args);
    }

}
