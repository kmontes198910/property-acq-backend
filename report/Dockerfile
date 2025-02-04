# Etapa de construcción: usa Maven para compilar la aplicación
FROM maven:3.9.4-eclipse-temurin-17 AS builder

WORKDIR /build

# Copia el archivo de configuración de Maven y ajusta el token
COPY rc-settings.xml settings.xml
ARG PACKAGE_TOKEN
RUN sed -i "s|GIT_TOKEN|$PACKAGE_TOKEN|g" settings.xml

# Copia el código fuente
COPY . /build

# Descarga las dependencias en modo offline y compila sin pruebas
RUN mvn dependency:go-offline -s settings.xml && \
    mvn clean package -DskipTests

# Etapa final: usa una imagen con JDK para soportar compilación de JasperReports
FROM eclipse-temurin:17-jdk
ENV TZ="America/Guayaquil"

# Copia el fat jar generado desde la etapa de construcción
COPY --from=builder /build/target/report-1.0.0-shaded.jar /app/report-1.0.0.jar

## Copia también los templates (asegúrate que la ruta sea la correcta)
#COPY src/main/resources/templates/receta.jrxml /app/resources/templates/receta.jrxml

EXPOSE 9909
ENTRYPOINT ["java", "-Xmx1024m", "-XX:+UseG1GC", "-jar", "/app/report-1.0.0.jar"]