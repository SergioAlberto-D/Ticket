package mx.edu.utez.tickets.controller.ticket.dto;

import lombok.*;
import mx.edu.utez.tickets.model.enums.PrioridadTicket;
import mx.edu.utez.tickets.model.enums.TicketEstado;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTicket {
    private Long id;
    private String descripcion;
    private TicketEstado estado;
    private PrioridadTicket prioridad;
    private Long usuarioId;
    private String usuarioNombre;
}
