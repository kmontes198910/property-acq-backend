#!/bin/bash
# Este script limpia los archivos rc-settings.xml para asegurar que no contengan tokens reales

# Primero, creamos una copia de seguridad
git branch -c dev dev-backup-$(date +%Y%m%d)

# Modificamos el archivo rc-settings.xml para usar la variable de entorno
echo '<?xml version="1.0" encoding="UTF-8"?>
<settings>
    <activeProfiles>
        <activeProfile>github</activeProfile>
    </activeProfiles>
    <profiles>
        <profile>
            <id>github</id>
            <repositories>
                <repository>
                    <id>central</id>
                    <url>https://repo1.maven.org/maven2</url>
                </repository>
                <repository>
                    <id>github</id>
                    <url>https://maven.pkg.github.com/kmontes198910/scheduled</url>
                    <snapshots>
                        <enabled>true</enabled>
                    </snapshots>
                </repository>
            </repositories>
        </profile>
    </profiles>
    <servers>
        <server>
            <id>github</id>
            <username>kmontes198910</username>
            <password>${env.PACKAGE_TOKEN}</password>
        </server>
    </servers>
</settings>' > propertyAcqCenter/rc-settings.xml

# Hacer commit del cambio
git add propertyAcqCenter/rc-settings.xml
git commit -m "fix: usar variable de entorno para token en rc-settings.xml"

echo "==========================================="
echo "Se ha actualizado el archivo rc-settings.xml para utilizar la variable de entorno."
echo "Si es necesario para GitHub, utiliza 'git push --force origin dev' para forzar el push"
echo "==========================================="
