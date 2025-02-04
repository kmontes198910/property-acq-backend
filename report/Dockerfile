# Etapa 1: Construcción de la aplicación con Maven y Java 17
FROM eclipse-temurin:17-jdk AS builder

# Establece el directorio de trabajo
WORKDIR /build

# (Opcional) Si usas un archivo de configuración para Maven, como rc-settings.xml, y necesitas reemplazar tokens:
COPY rc-settings.xml settings.xml
ARG PACKAGE_TOKEN
RUN sed -i "s|GIT_TOKEN|$PACKAGE_TOKEN|g" settings.xml

# Copia el código fuente completo
COPY . /build

# Descarga dependencias y compila la aplicación generando el fat JAR (usando, por ejemplo, maven-shade-plugin)
RUN mvn dependency:go-offline -s settings.xml && \
    mvn clean package -DskipTests

# Etapa 2: Imagen de ejecución con JDK 17
FROM eclipse-temurin:17-jdk

# Configura la zona horaria (opcional)
ENV TZ="America/Guayaquil"

# Copia el fat JAR generado en la etapa de construcción
# Asegúrate de que el nombre del JAR coincida con el que genera tu proceso de compilación (por ejemplo, report-1.0.0-shaded.jar)
COPY --from=builder /build/target/report-1.0.0-shaded.jar /app/report-1.0.0.jar

# (Opcional) Copia recursos adicionales, como archivos de templates JasperReports (.jrxml)
COPY src/main/resources/templates/receta.jrxml /app/resources/templates/receta.jrxml

# Expone el puerto que utiliza la aplicación
EXPOSE 9909

# Comando de inicio de la aplicación
ENTRYPOINT ["java", "-Xmx1024m", "-XX:+UseG1GC", "-jar", "/app/report-1.0.0.jar"]