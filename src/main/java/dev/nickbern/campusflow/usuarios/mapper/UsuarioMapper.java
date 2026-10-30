package dev.nickbern.campusflow.usuarios.mapper;

import dev.nickbern.campusflow.usuarios.dto.UsuarioResponse;
import dev.nickbern.campusflow.usuarios.model.PreferenciasUsuario;
import dev.nickbern.campusflow.usuarios.model.Usuario;

public class UsuarioMapper {

    public UsuarioResponse toResponse(Usuario usuario) {

        if (usuario == null) return null;

        PreferenciasUsuario preferencia = usuario.getPreferencias();

        if (preferencia == null) {
            preferencia = new PreferenciasUsuario();
        }

        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nombre(usuario.getName())
                .email(usuario.getEmail())
                .rol(usuario.getRol())
                .tema(preferencia.getTema())
                .notificacionesActivas(preferencia.isNotificacionesActivas())
                .build();
    }

}
