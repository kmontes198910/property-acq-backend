# ==========================================
# Etapa 1: Construcción de la aplicación
# ==========================================
FROM maven:3.9.4-eclipse-temurin-17 AS builder

# Establece el directorio de trabajo
WORKDIR /build

# Copia el archivo de configuración de Maven (si existe)
COPY rc-settings.xml settings.xml

# Configura variables de entorno para Maven y token de paquete
ARG PACKAGE_TOKEN
RUN sed -i "s|GIT_TOKEN|$PACKAGE_TOKEN|g" settings.xml

# Copia los archivos del proyecto
COPY . /build

# Descarga dependencias en modo offline y compila la aplicación generando el fat JAR
RUN mvn dependency:go-offline -s settings.xml && \
    mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk

# Establece la zona horaria
ENV TZ="America/Guayaquil"

# Define el directorio de trabajo
WORKDIR /app

# Copia el JAR generado en la etapa de construcción
COPY --from=builder /build/target/report-1.0.0.jar /app/report-1.0.0.jar

# Copia archivos adicionales como plantillas de JasperReports
COPY src/main/resources/templates/receta.jrxml /app/resources/templates/receta.jrxml

# ==========================
# 🚀 PASOS PARA DEPURAR PROBLEMAS 🚀
# ==========================

# 1️⃣ Verifica si el JAR contiene JasperReports
RUN jar -tf /app/report-1.0.0.jar | grep "net/sf/jasperreports" || echo "❌ JasperReports no encontrado en el JAR"

# 2️⃣ Verifica si los archivos fueron copiados correctamente
RUN ls -lah /app && ls -lah /app/resources/templates/

# 3️⃣ Ejecuta la aplicación manualmente dentro del contenedor para probar antes de iniciar
CMD ["java", "-Xmx1024m", "-XX:+UseG1GC", "-jar", "/app/report-1.0.0.jar"]
CMD ["java", "-Xmx1024m", "-XX:+UseG1GC", "-cp", "/app/report-1.0.0.jar", "org.springframework.boot.loader.JarLauncher"]
# ==========================
# Expone el puerto de la aplicación
EXPOSE 9909
