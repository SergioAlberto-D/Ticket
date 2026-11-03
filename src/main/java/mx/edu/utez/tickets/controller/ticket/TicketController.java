package mx.edu.utez.tickets.controller.ticket;

import jakarta.validation.Valid;
import mx.edu.utez.tickets.controller.ticket.dto.CreateTicketRequest;
import mx.edu.utez.tickets.controller.ticket.dto.ReasignacionRequest;
import mx.edu.utez.tickets.controller.ticket.dto.ResponseTicket;
import mx.edu.utez.tickets.service.ticket.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<ResponseTicket> crearTicket(@Valid @RequestBody CreateTicketRequest request) {
        return ResponseEntity.status(201).body(ticketService.crearTicket(request));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<ResponseTicket> actualizarEstadoTicket(@PathVariable Integer id) {
        return ResponseEntity.status(201).body(ticketService.actualizarEstadoTicket(id));
    }

    @PatchMapping("/{id}/prioridad")
    public ResponseEntity<ResponseTicket> reasignarPrioridad(
            @PathVariable Integer id,
            @Valid @RequestBody ReasignacionRequest payload) {
        return ResponseEntity.status(201).body(ticketService.reasignarPrioridad(id,payload));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ResponseTicket>> obtenerTicketsUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.status(201).body(ticketService.obtenerTicketsUsuario(usuarioId));
    }
}