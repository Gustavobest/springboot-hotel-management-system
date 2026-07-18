# 🏨 Hotel Management API

API REST desarrollada con **Java 17 + Spring Boot** para la gestión de hoteles.

El proyecto implementa autenticación JWT, autorización basada en roles, arquitectura por capas, validaciones, pruebas automatizadas y documentación con Swagger.

---

# 🚀 Tecnologías

- Java 17
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- Swagger / OpenAPI
- JUnit 5
- Mockito
- JaCoCo

---

# 📂 Arquitectura

```
Controller
│
Service
│
Repository
│
Entity
│
DTO
│
Specification
│
Validation
│
Exception
│
Security
```

---

# 📌 Funcionalidades

## Usuarios

- Crear usuario
- Editar usuario
- Eliminar usuario
- Buscar por ID
- Listar usuarios
- Paginación
- Búsqueda dinámica

---

## Habitaciones

- Crear habitación
- Editar habitación
- Eliminar habitación
- Buscar habitación
- Listar habitaciones
- Paginación
- Búsqueda por nombre

---

## Reservas

- Crear reserva
- Editar reserva
- Cancelar reserva
- Buscar reserva
- Listar reservas
- Paginación
- Búsqueda dinámica
- Validación de solapamiento de fechas

---

# 🔐 Seguridad

- JWT Authentication
- Spring Security
- Roles

```
ADMIN
USER
```

Uso de:

- AuthenticationManager
- PasswordEncoder
- JwtAuthenticationFilter
- SecurityFilterChain

---

# 📄 Documentación API

Swagger disponible en:

```
http://localhost:8080/swagger-ui/index.html
```

---

# 🧪 Testing

Se implementaron pruebas para:

- Unit Test (Service)
- Controller Test (MockMvc)
- Repository Test (JPA)
- Integration Test

Herramientas utilizadas:

- JUnit 5
- Mockito
- MockMvc
- JaCoCo

---

# 📊 Cobertura de Código

El proyecto utiliza JaCoCo para medir la cobertura de pruebas.

Ejecutar:

```bash
./mvnw clean verify
```

El reporte se genera automáticamente en:

```
target/site/jacoco/index.html
```

### Cobertura

<img width="1224" height="412" alt="image" src="https://github.com/user-attachments/assets/0b16b1bd-067a-4a8f-b3e1-f9c5317c4c24" />


```
/images/jacoco-report.png
```

---

# ⚙️ Instalación

Clonar repositorio

```bash
git clone https://github.com/TU_USUARIO/hotel-management.git
```

Entrar al proyecto

```bash
cd hotel-management
```

Compilar

```bash
./mvnw clean install
```

Ejecutar

```bash
./mvnw spring-boot:run
```

---

# 🗄 Base de Datos

MySQL

Configurar en:

```
application.properties
```

---

# 📁 Estructura del Proyecto

```
src
 ├── controller
 ├── service
 ├── repository
 ├── entity
 ├── dto
 ├── security
 ├── validation
 ├── specification
 ├── exception
 └── config
```

---

# 👨‍💻 Autor

**Gustavo Diego Quiroz**

Backend Developer (Java | Spring Boot)

LinkedIn:
(agrega aquí tu perfil)

GitHub:
(agrega aquí tu GitHub)
