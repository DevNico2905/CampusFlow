package dev.nickbern.campusflow.usuarios.controller;
import dev.nickbern.campusflow.usuarios.dto.ActualizarUsuarioRequest;
import dev.nickbern.campusflow.usuarios.dto.CrearUsuarioRequest;
import dev.nickbern.campusflow.usuarios.dto.UsuarioResponse;
import dev.nickbern.campusflow.usuarios.model.Usuario;
import dev.nickbern.campusflow.usuarios.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    // POST
    @PostMapping
    public ResponseEntity<UsuarioResponse> guardarUsuario(@Valid @RequestBody CrearUsuarioRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardarUsuario(request));
    }

    // GET
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> findAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> actualizarUsuario(@PathVariable Long id,
                                                             @Valid @RequestBody ActualizarUsuarioRequest request){
        return ResponseEntity.ok(usuarioService.actualizarUsuario(id, request));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id){
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }


}
