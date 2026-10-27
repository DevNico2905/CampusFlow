package dev.nickbern.campusflow.cursos.service;

import dev.nickbern.campusflow.cursos.model.Curso;
import dev.nickbern.campusflow.cursos.repository.CursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CursoService {

    private final CursoRepository cursoRepository;

    public List<Curso> findAll() {
        return cursoRepository.findAll();
    }
}
