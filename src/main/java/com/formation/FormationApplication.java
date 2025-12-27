package com.formation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application
 */
@SpringBootApplication
public class FormationApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(FormationApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("Application de Gestion de Formation");
        System.out.println("Service d'Optimisation de Planning");
        System.out.println("========================================\n");
    }
}
