# Etapa de construcción: usa Maven con OpenJDK 17 para compilar la aplicación
FROM maven:3.9.4-eclipse-temurin-17-alpine AS builder

# Establece el directorio de trabajo
WORKDIR /build

# Copia el archivo de configuración de Maven
COPY rc-settings.xml settings.xml

# Configura el token para el acceso a repositorios privados en settings.xml
ARG PACKAGE_TOKEN
RUN sed -i "s|GIT_TOKEN|$PACKAGE_TOKEN|g" settings.xml

# Copia los archivos del proyecto, incluyendo el pom.xml
COPY . .

# Compila las dependencias en modo offline y construye el paquete sin ejecutar pruebas
RUN mvn dependency:go-offline -s settings.xml && \
    mvn clean package -DskipTests

# Etapa final: usa Distroless para una imagen ligera y segura en producción
FROM gcr.io/distroless/java17-debian11

# Configura la zona horaria
ENV TZ="America/Guayaquil"

# Copia el archivo JAR generado en la etapa de construcción
COPY --from=builder /build/target/treatments-1.0.0.jar /app/treatments-1.0.0.jar

# Expone el puerto utilizado por la aplicación
EXPOSE 9906

# Comando de inicio para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/treatments-1.0.0.jar"]

