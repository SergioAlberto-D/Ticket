package mx.edu.utez.tickets.controller.ticket.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import mx.edu.utez.tickets.model.enums.PrioridadTicket;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReasignacionRequest {
    @NotNull(message = "La nueva prioridad es obligatoria (BAJA, MEDIA, ALTA)")
    private PrioridadTicket prioridad;
}
