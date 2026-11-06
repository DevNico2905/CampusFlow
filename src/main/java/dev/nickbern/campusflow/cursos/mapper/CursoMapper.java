package dev.nickbern.campusflow.cursos.mapper;

import dev.nickbern.campusflow.cursos.dto.CursoResponse;
import dev.nickbern.campusflow.cursos.model.Curso;

public class CursoMapper {

    public static CursoResponse toResponse(Curso curso) {

        return CursoResponse.builder()
                .id(curso.getId())
                .nombre(curso.getName())
                .instructorNombre(curso.getInstructor() != null ? curso.getName() : null)
                .cantidadEstudiantes(curso.getEstudiantes().size())
                .build();
    }
}