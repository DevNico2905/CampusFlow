package dev.nickbern.campusflow.usuarios.service;
import dev.nickbern.campusflow.common.exception.EmailDuplicadoException;
import dev.nickbern.campusflow.common.exception.ResourceNotFoundException;
import dev.nickbern.campusflow.usuarios.dto.ActualizarUsuarioRequest;
import dev.nickbern.campusflow.usuarios.dto.CrearUsuarioRequest;
import dev.nickbern.campusflow.usuarios.dto.UsuarioResponse;
import dev.nickbern.campusflow.usuarios.mapper.UsuarioMapper;
import dev.nickbern.campusflow.usuarios.model.PreferenciasUsuario;
import dev.nickbern.campusflow.usuarios.model.Usuario;
import dev.nickbern.campusflow.usuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // CREATE
    @Transactional
    public UsuarioResponse guardarUsuario(CrearUsuarioRequest request){

        String email = request.email().strip().toLowerCase();

        if (usuarioRepository.existsByEmail(email)){
            throw new EmailDuplicadoException("Este email ya está registrado.");
        }

        Usuario usuario = new Usuario(
                request.name(),
                email,
                passwordEncoder.encode(request.password()),
                request.rol()
        );

        return UsuarioMapper.toResponse(usuarioRepository.save(usuario));

    }

    //READ
    public List<UsuarioResponse> findAll() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioMapper::toResponse)
                .toList();
    }

    //UPDATE
    @Transactional
    public UsuarioResponse actualizarUsuario(Long id, ActualizarUsuarioRequest request){

        Usuario usuarioDb = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un usuario con el id: " + id));

        usuarioDb.setName(request.nombre());

        if (usuarioDb.getPreferencias() == null) {
            usuarioDb.setPreferencias(new PreferenciasUsuario());
        }
        if (request.tema() != null) {
            usuarioDb.getPreferencias().setTema(request.tema());
        }
        if (request.notificacionesActivas() != null) {
            usuarioDb.getPreferencias().setNotificacionesActivas(request.notificacionesActivas());
        }

        Usuario actualizado = usuarioRepository.save(usuarioDb);

        return UsuarioMapper.toResponse(actualizado);
    }

    //DELETE
    public void eliminarUsuario(Long id){

        if (!usuarioRepository.existsById(id)){
            throw new ResourceNotFoundException("No existe un usuario con el id: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    //READ BY ID
    public UsuarioResponse buscarPorId(Long id){
        Usuario usuarioDb = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un usuario con el id: " + id));
        return UsuarioMapper.toResponse(usuarioDb);
    }
}
