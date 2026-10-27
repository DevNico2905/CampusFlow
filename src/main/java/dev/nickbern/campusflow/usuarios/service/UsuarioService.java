package dev.nickbern.campusflow.usuarios.service;
import dev.nickbern.campusflow.usuarios.model.Usuario;
import dev.nickbern.campusflow.usuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

}
