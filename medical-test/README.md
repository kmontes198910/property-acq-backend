# Medical Test Service

Este microservicio forma parte del ecosistema Medinec y gestiona las órdenes de exámenes médicos y sus resultados.

## Estructura del proyecto

El proyecto sigue una arquitectura hexagonal con las siguientes capas:

- **Domain**: Contiene las entidades de dominio, interfaces de repositorio y servicios de dominio.
- **Application**: Contiene la implementación de servicios, comandos, queries y DTOs.
- **Infrastructure**: Contiene adaptadores como controladores REST, entidades JPA, mappers y configuraciones.

## Características principales

- Gestión de órdenes de exámenes médicos
- Seguimiento de exámenes individuales
- Registro de resultados de exámenes
- API REST con documentación OpenAPI/Swagger
- Persistencia en PostgreSQL con migraciones automáticas usando Flyway

## Requisitos

- Java 17
- Maven
- Docker (opcional, para ejecutar PostgreSQL local)

## Configuración y ejecución

### Base de datos

Puede iniciar una base de datos PostgreSQL local con Docker:

```bash
docker-compose up -d
```

### Compilación

```bash
./mvnw clean package
```

### Ejecución local

```bash
./mvnw spring-boot:run
```

## Documentación API

Una vez iniciada la aplicación, puede acceder a la documentación Swagger en:

```
http://localhost:8086/api/medical-test/swagger-ui.html
```

## Integraciones

Este servicio se integra con:
- Servicio de pacientes (para obtener datos del paciente)
- Servicio de identidad (para autenticación y autorización)

## Estructura de datos

### Entidades principales

1. **ExaminationOrder**: Representa una orden médica para realizar exámenes.
2. **Examination**: Representa un examen médico específico.

### Relaciones

- Una orden de exámenes (ExaminationOrder) puede contener múltiples exámenes (Examination).
- Cada examen está asociado a una única orden.

## Endpoints principales

### Órdenes de exámenes
- `POST /orders`: Crear una nueva orden
- `GET /orders/{id}`: Obtener orden por ID
- `GET /orders`: Listar órdenes (con filtros)
- `DELETE /orders/{id}`: Eliminar orden

### Exámenes
- `POST /examinations`: Crear un nuevo examen
- `PUT /examinations/{id}`: Actualizar un examen
- `GET /examinations/{id}`: Obtener examen por ID
- `GET /examinations`: Listar exámenes (con filtros)
- `DELETE /examinations/{id}`: Eliminar examen
- `POST /examinations/{id}/complete`: Marcar un examen como completado con resultados
