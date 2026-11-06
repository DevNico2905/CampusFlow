package dev.nickbern.campusflow.cursos.service;

import dev.nickbern.campusflow.common.exception.ResourceNotFoundException;
import dev.nickbern.campusflow.cursos.dto.CrearCursoRequest;
import dev.nickbern.campusflow.cursos.dto.CursoResponse;
import dev.nickbern.campusflow.cursos.mapper.CursoMapper;
import dev.nickbern.campusflow.cursos.model.Curso;
import dev.nickbern.campusflow.cursos.repository.CursoRepository;
import dev.nickbern.campusflow.usuarios.model.RolUsuario;
import dev.nickbern.campusflow.usuarios.model.Usuario;
import dev.nickbern.campusflow.usuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CursoService {

    private final CursoRepository cursoRepository;
    private final UsuarioRepository usuarioRepository;

    public CursoResponse crear(CrearCursoRequest request) {

        Usuario instructor = usuarioRepository.findById(request.instructorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe un usuario con id " + request.instructorId()));

        if (instructor.getRol() != RolUsuario.INSTRUCTOR){
            throw new ResourceNotFoundException("El Id: " + request.instructorId() + " no es de un instructor");
        }

        Curso curso = new Curso();
        curso.setName(request.nombre());
        curso.setInstructor(instructor);

        Curso guardado = cursoRepository.save(curso);
        return CursoMapper.toResponse(guardado);
    }

    public List<CursoResponse> listarCursos() {
        return cursoRepository.findAll()
                .stream()
                .map(CursoMapper::toResponse)
                .toList();
    }

    public CursoResponse inscribirEstudiante(Long cursoId, Long estudianteId) {
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un curso con id " + cursoId));

        Usuario estudiante = usuarioRepository.findById(estudianteId)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un usuario con id " + estudianteId));

        curso.getEstudiantes().add(estudiante);
        Curso actualizado = cursoRepository.save(curso);
        return CursoMapper.toResponse(actualizado);
    }
}
