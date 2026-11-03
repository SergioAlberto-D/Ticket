package mx.edu.utez.tickets.model.ticket;

import jakarta.persistence.*;
import lombok.*;
import mx.edu.utez.tickets.model.enums.PrioridadTicket;
import mx.edu.utez.tickets.model.enums.TicketEstado;
import mx.edu.utez.tickets.model.persona.Persona;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketEstado estado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PrioridadTicket prioridad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Persona usuario;

}
