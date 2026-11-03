package mx.edu.utez.tickets.service.ticket;

import jakarta.transaction.Transactional;
import mx.edu.utez.tickets.controller.ticket.dto.CreateTicketRequest;
import mx.edu.utez.tickets.controller.ticket.dto.ReasignacionRequest;
import mx.edu.utez.tickets.controller.ticket.dto.ResponseTicket;
import mx.edu.utez.tickets.exception.custom.CustomBadRequestException;
import mx.edu.utez.tickets.model.enums.TicketEstado;
import mx.edu.utez.tickets.model.persona.Persona;
import mx.edu.utez.tickets.model.ticket.Ticket;
import mx.edu.utez.tickets.repository.persona.PersonaRepository;
import mx.edu.utez.tickets.repository.ticket.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketService {

    private final TicketRepository repositorioT;
    private final PersonaRepository repositorioP;

    public TicketService(TicketRepository repositorioT, PersonaRepository repositorioP) {
        this.repositorioT = repositorioT;
        this.repositorioP = repositorioP;
    }

    @Transactional
    public ResponseTicket crearTicket(CreateTicketRequest payload) {
        Persona usuario = repositorioP.findById(payload.getUsuarioId())
                .orElseThrow(() -> new CustomBadRequestException("El usuario con ID " + payload.getUsuarioId() + " no existe"));

        if (!Boolean.TRUE.equals(usuario.getActivo())) {
            throw new CustomBadRequestException("No se puede crear el ticket: el usuario está inactivo");
        }

        Ticket ticket = new Ticket();
        ticket.setDescription(payload.getDescripcion());
        ticket.setPrioridad(payload.getPrioridad());
        ticket.setEstado(TicketEstado.ABIERTO);
        ticket.setUsuario(usuario);

        Ticket guardado = repositorioT.save(ticket);

        ResponseTicket response = new ResponseTicket();
        response.setId(guardado.getId() != null ? guardado.getId().longValue() : null);
        response.setDescripcion(guardado.getDescription());
        response.setEstado(guardado.getEstado());
        response.setPrioridad(guardado.getPrioridad());
        response.setUsuarioId(guardado.getUsuario().getId());
        response.setUsuarioNombre(guardado.getUsuario().getNombre());

        return response;
    }

    @Transactional
    public ResponseTicket actualizarEstadoTicket(Integer id) {
        Ticket ticket = repositorioT.findById(id)
                .orElseThrow(() -> new CustomBadRequestException("El ticket con ID " + id + " no existe"));

        if (ticket.getEstado() == TicketEstado.CERRADO) {
            throw new CustomBadRequestException("El ticket ya está CERRADO y no se puede modificar");
        }

        if (ticket.getEstado() == TicketEstado.ABIERTO) {
            ticket.setEstado(TicketEstado.EN_PROCESO);
        } else if (ticket.getEstado() == TicketEstado.EN_PROCESO) {
            ticket.setEstado(TicketEstado.CERRADO);
        }

        Ticket guardado = repositorioT.save(ticket);

        ResponseTicket response = new ResponseTicket();
        response.setId(guardado.getId() != null ? guardado.getId().longValue() : null);
        response.setDescripcion(guardado.getDescription());
        response.setEstado(guardado.getEstado());
        response.setPrioridad(guardado.getPrioridad());
        response.setUsuarioId(guardado.getUsuario().getId());
        response.setUsuarioNombre(guardado.getUsuario().getNombre());

        return response;
    }

    @Transactional
    public ResponseTicket reasignarPrioridad(Integer id, ReasignacionRequest dto) {
        Ticket ticket = repositorioT.findById(id)
                .orElseThrow(() -> new CustomBadRequestException("El ticket con ID " + id + " no existe"));

        if (ticket.getEstado() == TicketEstado.CERRADO) {
            throw new CustomBadRequestException("No se puede modificar la prioridad: el ticket se encuentra CERRADO");
        }

        ticket.setPrioridad(dto.getPrioridad());
        Ticket guardado = repositorioT.save(ticket);

        ResponseTicket response = new ResponseTicket();
        response.setId(guardado.getId() != null ? guardado.getId().longValue() : null);
        response.setDescripcion(guardado.getDescription());
        response.setEstado(guardado.getEstado());
        response.setPrioridad(guardado.getPrioridad());
        response.setUsuarioId(guardado.getUsuario().getId());
        response.setUsuarioNombre(guardado.getUsuario().getNombre());

        return response;
    }


    @Transactional
    public List<ResponseTicket> obtenerTicketsUsuario(Long usuarioId) {
        if (!repositorioP.existsById(usuarioId)) {
            throw new CustomBadRequestException("El usuario con ID " + usuarioId + " no existe");
        }

        List<Ticket> tickets = repositorioT.findByUsuarioId(usuarioId);
        List<ResponseTicket> listaResponse = new ArrayList<>();

        for (Ticket ticket : tickets) {
            ResponseTicket response = new ResponseTicket();
            response.setId(ticket.getId() != null ? ticket.getId().longValue() : null);
            response.setDescripcion(ticket.getDescription());
            response.setEstado(ticket.getEstado());
            response.setPrioridad(ticket.getPrioridad());
            response.setUsuarioId(ticket.getUsuario().getId());
            response.setUsuarioNombre(ticket.getUsuario().getNombre());
            listaResponse.add(response);
        }

        return listaResponse;
    }
}