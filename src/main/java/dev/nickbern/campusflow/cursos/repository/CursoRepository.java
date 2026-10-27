package dev.nickbern.campusflow.cursos.repository;
import dev.nickbern.campusflow.cursos.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
}
