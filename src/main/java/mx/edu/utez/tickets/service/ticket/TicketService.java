package mx.edu.utez.tickets.service.persona;

import mx.edu.utez.tickets.repository.persona.PersonaRepository;
import mx.edu.utez.tickets.repository.ticket.TicketRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonaService {
    private final TicketRepository ticketRepository;
    private final PersonaRepository usuarioRepository;

    public TicketService(TicketRepository ticketRepository, PersonaRepository usuarioRepository) {
        this.ticketRepository = ticketRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // 1. crearTicket (25 pts)
    @Transactional
    public TicketResponseDTO crearTicket(CrearTicketDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getTicketServiceUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("El usuario con ID " + dto.getUsuarioId() + " no existe"));

        if (!Boolean.TRUE.equals(usuario.getActivo())) {
            throw new BusinessRuleException("No se puede crear el ticket: el usuario está inactivo");
        }

        Ticket ticket = Ticket.builder()
                .descripcion(dto.getDescripcion().trim())
                .prioridad(dto.getPrioridad())
                .estado(EstadoTicket.ABIERTO)
                .usuario(usuario)
                .build();

        return toDto(ticketRepository.save(ticket));
    }

    // 2. actualizarEstadoTicket (25 pts)
    @Transactional
    public TicketResponseDTO actualizarEstadoTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El ticket con ID " + id + " no existe"));

        if (ticket.getEstado() == EstadoTicket.CERRADO) {
            throw new BusinessRuleException("El ticket ya está CERRADO y no se puede modificar");
        }

        if (ticket.getEstado() == EstadoTicket.ABIERTO) {
            ticket.setEstado(EstadoTicket.EN_PROCESO);
        } else if (ticket.getEstado() == EstadoTicket.EN_PROCESO) {
            ticket.setEstado(EstadoTicket.CERRADO);
        }

        return toDto(ticketRepository.save(ticket));
    }

    // 3. reasignarPrioridad (20 pts)
    @Transactional
    public TicketResponseDTO reasignarPrioridad(Long id, ActualizarPrioridadDTO dto) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El ticket con ID " + id + " no existe"));

        if (ticket.getEstado() == EstadoTicket.CERRADO) {
            throw new BusinessRuleException("No se puede modificar la prioridad: el ticket se encuentra CERRADO");
        }

        ticket.setPrioridad(dto.getPrioridad());
        return toDto(ticketRepository.save(ticket));
    }

    // 5. obtenerTicketsUsuario (10 pts)
    @Transactional(readOnly = true)
    public List<TicketResponseDTO> obtenerTicketsUsuario(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResourceNotFoundException("El usuario con ID " + usuarioId + " no existe");
        }

        return ticketRepository.findByUsuarioId(usuarioId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private TicketResponseDTO toDto(Ticket ticket) {
        return TicketResponseDTO.builder()
                .id(ticket.getId())
                .descripcion(ticket.getDescripcion())
                .estado(ticket.getEstado())
                .prioridad(ticket.getPrioridad())
                .usuarioId(ticket.getUsuario().getId())
                .usuarioNombre(ticket.getUsuario().getNombre())
                .build();
    }
}
