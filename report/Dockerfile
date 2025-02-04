# Etapa de construcción: utiliza Maven para compilar la aplicación
FROM maven:3.9.4-eclipse-temurin-17 AS builder

# Establece el directorio de trabajo
WORKDIR /build

# Copia el archivo de configuración de Maven
COPY rc-settings.xml settings.xml

# Configura variables de entorno para Maven y token de paquete
# Configura el token para el acceso a repositorios privados en settings.xml
ARG PACKAGE_TOKEN
RUN sed -i "s|GIT_TOKEN|$PACKAGE_TOKEN|g" settings.xml

# Copia los archivos de proyecto
COPY . /build

# Compila las dependencias en modo offline y construye el paquete sin ejecutar pruebas
RUN mvn dependency:go-offline -s settings.xml && \
    mvn clean package -DskipTests

# Etapa final: imagen ligera de distroless
FROM gcr.io/distroless/java17-debian11

# Establece la zona horaria
ENV TZ="America/Guayaquil"

# Copia el JAR generado desde la etapa de construcción
COPY --from=builder /build/target/report-1.0.0.jar /app/report-1.0.0.jar
COPY src/main/resources/templates/receta.jrxml /app/resources/templates/receta.jrxml
# Expone el puerto que utiliza la aplicación
EXPOSE 9909

# Variables de entorno para depuración y monitoreo
ENV JAVA_TOOL_OPTIONS="-Xmx1024m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/app/ -Dlogging.level.org.springframework=DEBUG"

# Comando de inicio de la aplicación con más memoria y recolección de basura mejorada
ENTRYPOINT ["java", "-Xmx1024m", "-XX:+UseG1GC", "-jar", "/app/report-1.0.0.jar"]