package dev.nickbern.campusflow.usuarios.repository;

import dev.nickbern.campusflow.usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
