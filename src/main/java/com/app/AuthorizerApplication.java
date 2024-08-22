package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.app")
public class AuthorizerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizerApplication.class, args);
    }
}