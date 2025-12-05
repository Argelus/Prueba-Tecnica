package com.tickets.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Se activa automáticamente cuando falla una validación
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarErroresDeValidacion(MethodArgumentNotValidException ex) {

        Map<String, String> errores = new HashMap<>();

        // Extraemos solo los mensajes amigables
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }

        // Construimos la respuesta JSON
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("status", "error");
        respuesta.put("data", errores);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }
}