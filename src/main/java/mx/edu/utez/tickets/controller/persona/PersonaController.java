package mx.edu.utez.tickets.controller.persona;

import mx.edu.utez.tickets.controller.persona.dto.PersonaResponseDTO;
import mx.edu.utez.tickets.service.persona.PersonaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/personas")
public class PersonaController {

    private final PersonaService personaService;

    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }


    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<PersonaResponseDTO> desactivarUsuario(@PathVariable Long id) {
        return ResponseEntity.status(201).body(personaService.desactivarUsuario(id));
    }
}