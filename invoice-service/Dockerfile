FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copiar el archivo de configuración de Maven
COPY ./rc-settings.xml /root/.m2/settings.xml

# Copiar POM y archivos de dependencias locales
COPY pom.xml .
COPY libs/ ./libs/
COPY repository/ ./repository/

# Variables para las rutas de las librerías
ENV LIBS_DIR=/app/libs
ENV REPOSITORY_DIR=/app/repository

# Comandos para instalar librerías locales en el repositorio
RUN mvn install:install-file -Dfile=${LIBS_DIR}/E-Facturacion-1.0.0.jar -DgroupId=com.facturacion -DartifactId=e-facturacion -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true -DlocalRepositoryPath=${REPOSITORY_DIR}

# Etapa de caché para las dependencias
RUN mvn dependency:go-offline -B

# Copiar el código fuente
COPY src/ ./src/

# Compilar la aplicación
RUN mvn clean package -DskipTests

# Etapa final con JRE más ligero
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copiar el JAR compilado
COPY --from=build /app/target/*.jar app.jar

# Crear un usuario no root para ejecutar la aplicación
RUN addgroup --system --gid 1001 appgroup \
    && adduser --system --uid 1001 --ingroup appgroup appuser \
    && chown -R appuser:appgroup /app

USER appuser

# Variables de entorno para configuración de memoria en Java
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=70"

# Puerto de la aplicación
EXPOSE 8080

# Verificación de salud
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Comando para ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]