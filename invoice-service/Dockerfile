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
RUN mvn install:install-file -Dfile=${LIBS_DIR}/hospital-jreport-5.0.0.jar -DgroupId=medical.com -DartifactId=hospital-jreport -Dversion=5.0.0 -Dpackaging=jar -DgeneratePom=true -DlocalRepositoryPath=${REPOSITORY_DIR}
RUN mvn install:install-file -Dfile=${LIBS_DIR}/hospital-core-2.0.1.jar -DgroupId=medical.kynsoft.com -DartifactId=hospital-core -Dversion=2.0.1 -Dpackaging=jar -DgeneratePom=true -DlocalRepositoryPath=${REPOSITORY_DIR}
RUN mvn install:install-file -Dfile=${LIBS_DIR}/hospital-utils-2.0.0.jar -DgroupId=medical.kynsoft.com -DartifactId=hospital-utils -Dversion=2.0.0 -Dpackaging=jar -DgeneratePom=true -DlocalRepositoryPath=${REPOSITORY_DIR}
RUN mvn install:install-file -Dfile=${LIBS_DIR}/hospital-global-1.0.0.jar -DgroupId=com.global.hospital -DartifactId=hospital-global -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true -DlocalRepositoryPath=${REPOSITORY_DIR}

# Instalar librerías locales desde Untitled-1.ini (como respaldo)
COPY Untitled-1.ini .
RUN sed -i 's|/Users/keimermontes/Development/medinec/scheduled/invoice-service|/app|g' Untitled-1.ini
RUN chmod +x Untitled-1.ini
RUN /bin/sh Untitled-1.ini

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
EXPOSE 7777

# Verificación de salud
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:7777/actuator/health || exit 1

# Comando para ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]