package com.servosys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringValidationApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringValidationApplication.class, args);
        System.out.println("✅ Student CRUD Application Started Successfully!");
        System.out.println("🔗 H2 Console: http://localhost:8080/h2-console");
        System.out.println("🔗 API Docs: http://localhost:8080/api/v1/students");
    }
}