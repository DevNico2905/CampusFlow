package dev.nickbern.campusflow.usuarios.dto;
import dev.nickbern.campusflow.usuarios.model.RolUsuario;
import dev.nickbern.campusflow.usuarios.model.TemaVisual;
import lombok.Builder;

@Builder
public record UsuarioResponse(
        Long id,
        String nombre,
        String email,
        RolUsuario rol,
        TemaVisual tema,
        boolean notificacionesActivas
) {
}
