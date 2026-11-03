package mx.edu.utez.tickets.service.persona;

import jakarta.transaction.Transactional;
import mx.edu.utez.tickets.controller.persona.dto.PersonaResponseDTO;
import mx.edu.utez.tickets.exception.custom.CustomBadRequestException;
import mx.edu.utez.tickets.model.persona.Persona;
import mx.edu.utez.tickets.repository.persona.PersonaRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonaService {

    private final PersonaRepository repositorio;

    public PersonaService(PersonaRepository repositorio) {
        this.repositorio = repositorio;
    }

    @Transactional
    public PersonaResponseDTO desactivarUsuario(Long id) {
        PersonaResponseDTO responseDTO = new PersonaResponseDTO();
        Persona usuario = repositorio.findById(id)
                .orElseThrow(() -> new CustomBadRequestException("El usuario con ID " + id + " no fue encontrado"));
        usuario.setActivo(false);
        responseDTO.setId(usuario.getId());
        responseDTO.setNombre(usuario.getNombre());
        responseDTO.setActivo(usuario.getActivo());
        repositorio.save(usuario);
        return responseDTO;
    }
}