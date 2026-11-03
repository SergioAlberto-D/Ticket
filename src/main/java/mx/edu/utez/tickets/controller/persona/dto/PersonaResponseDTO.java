package mx.edu.utez.tickets.controller.persona.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonaResponseDTO {
    private Long id;
    private String nombre;
    private Boolean activo;
}