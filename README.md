# CampusFlow

Backend de una plataforma **SaaS** para la gestión de cursos en academias, construido como un **monolito modular** con Java 17 y Spring Boot.

El objetivo de esta etapa es dejar una base que pueda crecer de una academia a muchas sin que cambiar un área del negocio (por ejemplo, pagos) desordene las demás (usuarios, cursos).

> Proyecto desarrollado en el programa **Java AI Engineer** (Dev Senior · Módulo 4).

---

## Contexto del producto

CampusFlow se ofrece como un servicio por internet: la academia usa la plataforma sin instalar ni administrar el backend, y el proveedor lo opera, mantiene y actualiza.

Esta versión no es todavía un servicio listo para producción. Construye la base del backend: organización por dominios, perfiles de entorno y manejo centralizado de errores.

## Stack

| Tecnología | Uso |
|---|---|
| Java 21 | Lenguaje |
| Spring Boot 3.3.4 | Framework base |
| Spring Web | API REST |
| Spring Data JPA / Hibernate | Persistencia |
| PostgreSQL | Base de datos |
| Maven | Build y dependencias |

## Arquitectura

### Monolito modular por dominio

El código se agrupa por **área de negocio**, no por capa técnica. Cada dominio contiene sus propias capas, y todo se despliega junto en un único JAR.

```
com.devsenior.campusflow
├─ CampusflowApplication.java
├─ common/
│  └─ exception/          # Excepciones y handler global compartidos
├─ usuarios/
│  ├─ model/              # Usuario
│  ├─ repository/         # UsuarioRepository
│  ├─ service/            # UsuarioService
│  └─ controller/         # UsuarioController
└─ cursos/
   ├─ model/              # Curso
   ├─ repository/         # CursoRepository
   ├─ service/            # CursoService
   └─ controller/         # CursoController
```

Los paquetes **no son microservicios**: comparten proceso, base de datos y despliegue. La separación existe para localizar los cambios y controlar las dependencias.

### Responsabilidad de cada capa

| Capa | Responsabilidad | Límite |
|---|---|---|
| `controller` | Recibir la petición HTTP y devolver la respuesta | Delega en el service; no usa el repository |
| `service` | Coordinar el caso de uso y sus reglas | No depende de HTTP |
| `repository` | Acceder a los datos | No decide reglas de negocio |
| `model` | Representar la entidad | No depende del controller ni del service |

Las dependencias se inyectan **por constructor** con campos `final`.

### Recorrido de una petición

```
GET /api/usuarios
  → UsuarioController.listar()
  → UsuarioService.listarUsuarios()
  → UsuarioRepository.findAll()
  → Hibernate / MySQL (SELECT usuarios)
  ← List<Usuario>
```

## Endpoints

| Método | Ruta | Descripción | Respuesta |
|---|---|---|---|
| `GET` | `/api/usuarios` | Lista los usuarios | `200` · lista de usuarios |
| `GET` | `/api/cursos` | Lista los cursos | `200` · lista de cursos |

## Manejo de errores

Un `GlobalExceptionHandler` (`@RestControllerAdvice`) centraliza las respuestas de error. Extiende `ResponseEntityExceptionHandler` para conservar los estados estándar de Spring MVC.

| Situación | Estado |
|---|---|
| Recurso inexistente (`ResourceNotFoundException`) | `404` |
| Método no soportado (p. ej. `POST /api/usuarios`) | `405` |
| Petición mal formada | `400` |
| Error no controlado | `500` con mensaje genérico; el detalle queda en el log |

## Configuración por entornos

La configuración se separa por perfiles. Las credenciales no se guardan en el repositorio: se leen de variables de entorno.

| Archivo | Propósito |
|---|---|
| `application.properties` | Nombre de la aplicación |
| `application-dev.properties` | MySQL local, `ddl-auto=update`, SQL visible |
| `application-prod.properties` | Conexión por variables, `ddl-auto=validate`, SQL oculto |

| Variable | Perfil | Descripción |
|---|---|---|
| `SPRING_PROFILES_ACTIVE` | ambos | `dev` o `prod` |
| `DB_PASSWORD` | dev, prod | Contraseña de MySQL |
| `DB_URL` | prod | URL JDBC del servidor |
| `DB_USERNAME` | prod | Usuario de MySQL |

En producción `validate` comprueba que las tablas existan, pero no las crea ni las modifica.

## Ejecución local

### Requisitos

- JDK 17
- Maven (o el wrapper `mvnw` incluido)
- PostgreSQL en ejecución

### 1. Crear la base de datos

```sql
CREATE DATABASE IF NOT EXISTS campusflow_db;
```

### 2. Definir las variables de entorno

**PowerShell**

```powershell
$env:SPRING_PROFILES_ACTIVE = "dev"
$env:DB_PASSWORD = "tu_contraseña"
```

**Bash**

```bash
export SPRING_PROFILES_ACTIVE=dev
export DB_PASSWORD=tu_contraseña
```

En IntelliJ también puedes definirlas en *Run → Edit Configurations → Environment variables*.

### 3. Ejecutar

```bash
./mvnw spring-boot:run
```

La API queda disponible en `http://localhost:8080`.

### 4. Verificar

| Prueba | Resultado esperado |
|---|---|
| `GET http://localhost:8080/api/usuarios` | `200` |
| `GET http://localhost:8080/api/cursos` | `200` · `[]` en una tabla nueva |
| `POST http://localhost:8080/api/usuarios` | `405` |

## Hoja de ruta

- [x] Organización modular por dominios (`usuarios`, `cursos`)
- [x] Capa de servicio e inyección por constructor
- [x] Perfiles `dev` / `prod` con variables de entorno
- [x] Manejo centralizado de errores
- [ ] Roles y perfiles de usuario
- [ ] Relaciones entre usuarios y cursos (instructor, inscripciones)
- [ ] DTOs de entrada y salida
- [ ] Autenticación con JWT y permisos por rol
- [ ] Dominio de pagos y suscripciones
- [ ] Aislamiento de datos entre academias (multi-tenant)

## Autor

**Nicolás Bernal** · [GitHub](https://github.com/DevNico2905) · [nick-bern.com](https://nick-bern.com)

Material base del curso: Ana Alvarado, Dev Senior.