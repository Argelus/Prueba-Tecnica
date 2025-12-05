package com.tickets.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título es obligatorio")
    @Column(nullable = false)
    private String titulo;

    private String descripcion;

    @Enumerated(EnumType.STRING)
    private EstatusTicket estatus;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    public Ticket() {}

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
        if (this.estatus == null) {
            this.estatus = EstatusTicket.PENDIENTE;
        }
    }

    // --- Getters y Setters---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public EstatusTicket getEstatus() { return estatus; }
    public void setEstatus(EstatusTicket estatus) { this.estatus = estatus; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}