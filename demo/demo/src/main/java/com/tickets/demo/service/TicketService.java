package com.tickets.demo.service;

import com.tickets.demo.model.EstatusTicket;
import com.tickets.demo.model.Ticket;
import com.tickets.demo.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    // 1. Listar todos
    public List<Ticket> listarTodos() {
        return ticketRepository.findAll();
    }

    // 2. Guardar (sirve para Crear y para Editar si lleva ID)
    public Ticket guardar(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    // 3. Buscar por ID
    public Optional<Ticket> obtenerPorId(Long id) {
        return ticketRepository.findById(id);
    }

    // 4. Eliminar
    public void eliminar(Long id) {
        ticketRepository.deleteById(id);
    }

    // 5. Lógica específica para cambiar solo el estatus
    public Ticket cambiarEstatus(Long id, EstatusTicket nuevoEstatus) {
        // Primero buscamos si existe
        return ticketRepository.findById(id)
                .map(ticket -> {
                    ticket.setEstatus(nuevoEstatus);
                    return ticketRepository.save(ticket);
                })
                .orElse(null); // Retorna null si no encuentra el ticket
    }
}
