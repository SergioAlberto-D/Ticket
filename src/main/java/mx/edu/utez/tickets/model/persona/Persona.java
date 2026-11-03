package mx.edu.utez.tickets.model.persona;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "persona1")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Boolean activo;
}
