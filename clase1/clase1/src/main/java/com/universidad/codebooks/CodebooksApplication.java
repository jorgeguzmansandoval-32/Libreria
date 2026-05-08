package com.universidad.codebooks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// ═══════════════════════════════════════════════════
// CLASE 1 · CodebooksApplication.java
// Punto de entrada de la aplicación Spring Boot.
// ═══════════════════════════════════════════════════

// @SpringBootApplication combina tres anotaciones:
//   @Configuration        → esta clase puede declarar Beans
//   @EnableAutoConfiguration → configura automáticamente según pom.xml
//   @ComponentScan        → escanea @Service, @Repository, @Controller, etc.
@SpringBootApplication
public class CodebooksApplication {

    public static void main(String[] args) {
        // Inicia el contenedor Spring, levanta Tomcat embebido
        // y conecta con MySQL usando application.properties
        SpringApplication.run(CodebooksApplication.class, args);
    }
}
