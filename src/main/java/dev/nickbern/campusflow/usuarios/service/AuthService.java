package dev.nickbern.campusflow.usuarios.service;

import dev.nickbern.campusflow.common.exception.EmailDuplicadoException;
import dev.nickbern.campusflow.common.exception.ResourceNotFoundException;
import dev.nickbern.campusflow.security.JwtService;
import dev.nickbern.campusflow.usuarios.dto.AuthResponse;
import dev.nickbern.campusflow.usuarios.dto.LoginRequest;
import dev.nickbern.campusflow.usuarios.dto.RegistroRequest;
import dev.nickbern.campusflow.usuarios.model.RolUsuario;
import dev.nickbern.campusflow.usuarios.model.Usuario;
import dev.nickbern.campusflow.usuarios.repository.UsuarioRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository usuarioRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthResponse registrar(RegistroRequest request) {
        usuarioRepository.findByEmail(request.email()).ifPresent(usuarioExistente -> {
            throw new EmailDuplicadoException("Ya existe un usuario registrado con ese email");
        });

        Usuario usuario = new Usuario(
                request.nombre(),
                request.email(),
                passwordEncoder.encode(request.password()),
                RolUsuario.ESTUDIANTE
        );

        usuarioRepository.save(usuario);

        String token = jwtService.generarToken(usuario.getEmail(), usuario.getRol().name());
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(), request.password()));

        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResourceNotFoundException("No existe un usuario con ese email"));
        String token = jwtService.generarToken(usuario.getEmail(), usuario.getRol().name());
        return new AuthResponse(token);
    }
}