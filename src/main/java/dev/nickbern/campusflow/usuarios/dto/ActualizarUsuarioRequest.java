package dev.nickbern.campusflow.usuarios.dto;

import dev.nickbern.campusflow.usuarios.model.TemaVisual;
import jakarta.validation.constraints.NotBlank;

public record ActualizarUsuarioRequest(
        @NotBlank String nombre,
        TemaVisual tema,
        Boolean notificacionesActivas
) {
}