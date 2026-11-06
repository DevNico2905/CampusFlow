package dev.nickbern.campusflow.cursos.controller;

import dev.nickbern.campusflow.cursos.dto.CrearCursoRequest;
import dev.nickbern.campusflow.cursos.dto.CursoResponse;
import dev.nickbern.campusflow.cursos.model.Curso;
import dev.nickbern.campusflow.cursos.service.CursoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService cursoService;

    @PostMapping
    public ResponseEntity<CursoResponse> crearCurso(@Valid @RequestBody CrearCursoRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.crear(request));
    }



    @GetMapping
    public ResponseEntity<List<CursoResponse>> findAll() {
        return ResponseEntity.ok(cursoService.listarCursos());
    }

    @PostMapping("/{cursoId}/estudiantes/{estudianteId}")
    public ResponseEntity<CursoResponse> inscribirEstudiante(@PathVariable Long cursoId, @PathVariable Long estudianteId){
        return ResponseEntity.ok(cursoService.inscribirEstudiante(cursoId, estudianteId));
    }
}
