package com.example.poverty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PovertyDashboardApplication {
    public static void main(String[] args) {
        SpringApplication.run(PovertyDashboardApplication.class, args);
    }
}