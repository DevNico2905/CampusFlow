package dev.nickbern.campusflow.usuarios.dto;

import dev.nickbern.campusflow.usuarios.model.RolUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CrearUsuarioRequest(
        @NotBlank
        String name,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 8, message = "La contraseña debe tener mínimo 8 caracteres")
        String password,

        @NotNull
        RolUsuario rol
) {
}
