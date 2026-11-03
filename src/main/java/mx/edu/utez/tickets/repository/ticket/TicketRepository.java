package mx.edu.utez.tickets.repository.ticket;

import mx.edu.utez.tickets.model.ticket.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByUsuarioId(Long usuarioId);
}