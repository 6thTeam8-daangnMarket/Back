package com.sparta.clone_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CloneBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloneBackendApplication.class, args);
    }

}
