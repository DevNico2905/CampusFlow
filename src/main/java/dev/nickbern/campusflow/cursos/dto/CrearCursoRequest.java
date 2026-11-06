package dev.nickbern.campusflow.cursos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearCursoRequest(
        @NotBlank
        String nombre,

        @NotNull
        Long instructorId
) {
}
