package com.tickets.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
    System.out.println("--- Servidor de Tickets iniciado en el puerto 8080 ---");
}
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Sistema de Tickets")
                        .version("1.0")
                        .description("Documentación oficial para el sistema de To-Do List y Tickets"));
    }
}
