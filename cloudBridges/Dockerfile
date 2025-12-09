# Etapa de construcción: usa Maven con OpenJDK 17 para compilar la aplicación
FROM maven:3.9.4-eclipse-temurin-17-alpine AS builder

# Establece el directorio de trabajo
WORKDIR /build

ARG PACKAGE_TOKEN
COPY rc-settings.xml settings.xml

RUN if [ -f settings.xml ]; then \
    sed -i "s|GIT_TOKEN|${PACKAGE_TOKEN}|g" settings.xml; \
    fi

# Copia los archivos del proyecto, incluyendo el pom.xml
COPY . .

# Compila las dependencias en modo offline y construye el paquete sin ejecutar pruebas
RUN mvn dependency:go-offline -s settings.xml && \
    mvn clean package -DskipTests -s settings.xml

# Etapa final: usa Distroless para una imagen ligera y segura en producción
FROM gcr.io/distroless/java17-debian11

# Configura la zona horaria
ENV TZ="America/Guayaquil"

# Copia el archivo JAR generado en la etapa de construcción
COPY --from=builder /build/target/cloudBridges-1.0.0.jar /app/cloudBridges-1.0.0.jar

# Expone el puerto utilizado por la aplicación
EXPOSE 9909

# Comando de inicio para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/cloudBridges-1.0.0.jar"]