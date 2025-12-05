package com.tickets.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
    System.out.println("--- Servidor de Tickets iniciado en el puerto 8080 ---");
}
}
