# Property Acquisition Backend

Backend para el sistema de adquisición de propiedades. Este proyecto está compuesto por varios microservicios Spring Boot.

## Estructura del Proyecto

El proyecto está organizado en varios módulos:

- **calendar**: Servicio para la gestión de calendarios y citas
- **cirugia**: Servicio para la gestión de procedimientos quirúrgicos
- **cloudBridges**: Servicios para la interconexión con sistemas en la nube
- **config**: Servidor de configuración centralizada
- **digitalSignature**: Servicio para la firma digital de documentos
- **evaluation**: Servicio de evaluación
- **gateway**: API Gateway para la entrada unificada al sistema
- **hospitalization-service**: Servicio para gestionar hospitalizaciones
- **identity**: Servicio de autenticación y autorización
- **invoice-service**: Servicio para la gestión de facturas
- **medical-test**: Servicio para la gestión de pruebas médicas
- **patients**: Servicio para la gestión de pacientes
- **payment**: Servicio para procesamiento de pagos
- **propertyAcqCenter**: Servicio principal para la adquisición de propiedades
- **registry**: Servicio de registro y descubrimiento (Eureka)
- **report**: Servicio para generación de reportes
- **rrhh**: Servicio para gestión de recursos humanos
- **settings**: Configuraciones generales
- **share**: Servicio para compartir información
- **socketService**: Servicio de comunicación en tiempo real
- **treatments**: Servicio para gestión de tratamientos
- **wa-messaging**: Servicio de mensajería

## Requisitos

- Java 11+
- Maven
- Docker (opcional, para despliegue en contenedores)
- PostgreSQL/MySQL

## Configuración

Cada módulo contiene su propia configuración en archivos `application.properties` o `application.yml` en sus respectivos directorios `src/main/resources`.

## Instalación y Ejecución

1. Clonar el repositorio
2. Compilar el proyecto:
   ```
   mvn clean install
   ```
3. Ejecutar los servicios necesarios:
   ```
   cd <service-directory>
   mvn spring-boot:run
   ```

## Despliegue con Docker

```
docker-compose up -d
```

## Despliegue en Kubernetes

Consultar la documentación en la carpeta `kubernetes-config`.
