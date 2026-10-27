package dev.nickbern.campusflow.cursos.controller;

import dev.nickbern.campusflow.cursos.model.Curso;
import dev.nickbern.campusflow.cursos.service.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService cursoService;

    @GetMapping
    public List<Curso> findAll() {
        return cursoService.findAll();
    }
}
