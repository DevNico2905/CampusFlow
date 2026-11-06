package dev.nickbern.campusflow.cursos.dto;

import lombok.Builder;
import lombok.Setter;

@Builder
public record CursoResponse(
        Long id,
        String nombre,
        String instructorNombre,
        int cantidadEstudiantes
) {
}
