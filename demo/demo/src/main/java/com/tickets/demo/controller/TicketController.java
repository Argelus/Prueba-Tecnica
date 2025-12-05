package com.tickets.demo.controller;

import com.tickets.demo.model.EstatusTicket;
import com.tickets.demo.model.Ticket;
import com.tickets.demo.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "*")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    // Respuesta JSON
    private ResponseEntity<Map<String, Object>> generarRespuesta(String status, Object data, String mensaje) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("data", data);
        if (mensaje != null) response.put("message", mensaje);
        return ResponseEntity.ok(response);
    }

    // 1. Crear Ticket (POST) con validación
    @PostMapping
    public ResponseEntity<Map<String, Object>> crearTicket(@Valid @RequestBody Ticket ticket) {
        Ticket nuevoTicket = ticketService.guardar(ticket);
        return generarRespuesta("ok", nuevoTicket, "Ticket creado exitosamente");
    }

    // 2. Listar Tickets (GET)
    @GetMapping
    public ResponseEntity<Map<String, Object>> listarTickets() {
        List<Ticket> tickets = ticketService.listarTodos();
        return generarRespuesta("ok", tickets, null);
    }

    // 3. Obtener Ticket por ID (GET /{id})
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerTicket(@PathVariable Long id) {
        return ticketService.obtenerPorId(id)
                .map(ticket -> generarRespuesta("ok", ticket, null))
                .orElse(ResponseEntity.status(404).body(Map.of("status", "error", "message", "Ticket no encontrado")));
    }

    // 4. Editar Ticket (PUT /{id})
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> editarTicket(@PathVariable Long id, @RequestBody Ticket ticketDetalles) {
        return ticketService.obtenerPorId(id)
                .map(ticket -> {
                    ticket.setTitulo(ticketDetalles.getTitulo());
                    ticket.setDescripcion(ticketDetalles.getDescripcion());
                    ticket.setEstatus(ticketDetalles.getEstatus());
                    Ticket actualizado = ticketService.guardar(ticket);
                    return generarRespuesta("ok", actualizado, "Ticket actualizado");
                })
                .orElse(ResponseEntity.status(404).body(Map.of("status", "error", "message", "Ticket no encontrado")));
    }

    // 5. Eliminar (DELETE /{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarTicket(@PathVariable Long id) {
        if (ticketService.obtenerPorId(id).isPresent()) {
            ticketService.eliminar(id);
            // El PDF pide respuesta JSON incluso al borrar
            return generarRespuesta("ok", null, "Ticket eliminado correctamente");
        } else {
            return ResponseEntity.status(404).body(Map.of("status", "error", "message", "Ticket no encontrado"));
        }
    }

    // 6. Cambiar Estatus (PUT /{id}/estatus)
    @PutMapping("/{id}/estatus")
    public ResponseEntity<Map<String, Object>> cambiarEstatus(@PathVariable Long id, @RequestParam EstatusTicket estatus) {
        Ticket actualizado = ticketService.cambiarEstatus(id, estatus);
        if(actualizado != null) {
            return generarRespuesta("ok", actualizado, "Estatus actualizado");
        }
        return ResponseEntity.status(404).body(Map.of("status", "error", "message", "Ticket no encontrado"));
    }
}