package com.example.tempconv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.example.tempconv.repository")
public class TempconvApplication {

    public static void main(String[] args) {
        SpringApplication.run(TempconvApplication.class, args);
    }
}