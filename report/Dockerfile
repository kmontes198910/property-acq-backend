# ============================
# Etapa 1: Construcción (build)
# ============================
FROM maven:3.9.4-eclipse-temurin-17 AS builder

# Establece el directorio de trabajo
WORKDIR /build

# (Opcional) Copia el archivo de configuración de Maven y reemplaza el token si es necesario
COPY rc-settings.xml settings.xml
ARG PACKAGE_TOKEN
RUN sed -i "s|GIT_TOKEN|$PACKAGE_TOKEN|g" settings.xml

# Copia todo el código fuente al contenedor
COPY . /build

# Descarga las dependencias y compila la aplicación generando el fat JAR
# Se asume que en el pom.xml se utiliza el maven-shade-plugin para generar "report-1.0.0-shaded.jar"
RUN mvn dependency:go-offline -s settings.xml && \
    mvn clean package -DskipTests

# ============================
# Etapa 2: Ejecución (runtime)
# ============================
FROM eclipse-temurin:17-jdk

# Configura la zona horaria (opcional)
ENV TZ="America/Guayaquil"

# Crea el directorio de la aplicación
WORKDIR /app

# Copia el fat JAR generado en la etapa de construcción
# Verifica que el nombre coincida con el definido en tu pom.xml (en este caso, "report-1.0.0-shaded.jar")
COPY --from=builder /build/target/report-1.0.0-shaded.jar /app/report-1.0.0.jar

# (Opcional) Copia recursos adicionales, como plantillas JasperReports (.jrxml)
COPY src/main/resources/templates/receta.jrxml /app/resources/templates/receta.jrxml

# Expone el puerto que utiliza la aplicación
EXPOSE 9909

# Comando de inicio de la aplicación
ENTRYPOINT ["java", "-Xmx1024m", "-XX:+UseG1GC", "-jar", "/app/report-1.0.0.jar"]