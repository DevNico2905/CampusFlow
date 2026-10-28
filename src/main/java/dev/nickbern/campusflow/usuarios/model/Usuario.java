package dev.nickbern.campusflow.usuarios.model;

import dev.nickbern.campusflow.cursos.model.Curso;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private RolUsuario rol;

    @Embedded
    private PreferenciasUsuario preferencias = new PreferenciasUsuario();

    @OneToMany(mappedBy = "instructor")
    private List<Curso> cursosDictados = new ArrayList<>();

    @ManyToMany(mappedBy = "estudiantes")
    private List<Curso> cursosInscritos = new ArrayList<>();

}
