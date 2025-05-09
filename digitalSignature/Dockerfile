#FROM ghcr.io/graalvm/native-image:ol8-java17-22 AS builder
#
## Install tar and gzip to extract the Maven binaries
#RUN microdnf update \
#    && microdnf install --nodocs \
#    tar \
#    gzip \
#    && microdnf clean all \
#    && rm -rf /var/cache/yum
#
## Install Maven
## Source:
## 1) https://github.com/carlossg/docker-maven/blob/925e49a1d0986070208e3c06a11c41f8f2cada82/openjdk-17/Dockerfile
## 2) https://maven.apache.org/download.cgi
#ARG USER_HOME_DIR="/root"
#ARG SHA=deaa39e16b2cf20f8cd7d232a1306344f04020e1f0fb28d35492606f647a60fe729cc40d3cba33e093a17aed41bd161fe1240556d0f1b80e773abd408686217e
#ARG MAVEN_DOWNLOAD_URL=https://dlcdn.apache.org/maven/maven-3/3.9.4/binaries/apache-maven-3.9.4-bin.tar.gz
#
#RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
#    && curl -fsSL -o /tmp/apache-maven.tar.gz ${MAVEN_DOWNLOAD_URL} \
#    && echo "${SHA}  /tmp/apache-maven.tar.gz" | sha512sum -c - \
#    && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
#    && rm -f /tmp/apache-maven.tar.gz \
#    && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn
#
#ENV MAVEN_HOME /usr/share/maven/bin/
#ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"
#
## Set the working directory to /home/app
#WORKDIR /build
#
## Copy the source code into the image for building
#COPY . /build
#
## Setup environment vairables for maven resposiory
#ARG PACKAGE_TOKEN
#ENV PACKAGE_TOKEN ${PACKAGE_TOKEN}
#
## Build
#RUN mvn dependency:go-offline -f pom.xml
#RUN mvn --no-transfer-progress clean package -DskipTests
#
## The deployment Image
#FROM bellsoft/liberica-openjdk-alpine:17.0.6
#
#EXPOSE 8090
#
## Configurar Zona Horaria
#RUN apk upgrade --no-cache
#RUN apk add --no-cache tzdata
#ENV TZ="America/Guayaquil"
#
## Copy the native executable into the containers
#WORKDIR /app
#COPY --from=builder /build/target/digitalSignature-*.jar app.jar
#ENTRYPOINT [ "java", "-jar", "/app/app.jar" ]

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
COPY --from=builder /build/target/digitalSignature-0.0.1-SNAPSHOT.jar /app/digitalSignature-0.0.1-SNAPSHOT.jar
# Expone el puerto utilizado por la aplicación
EXPOSE 9909
# Comando de inicio para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/digitalSignature-0.0.1-SNAPSHOT.jar"]