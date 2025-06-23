#!/bin/bash
# Este script crea un nuevo repositorio limpio sin el historial problemático

echo "Creando un nuevo repositorio limpio..."

# Crear un directorio temporal para el nuevo repositorio
TEMP_DIR=$(mktemp -d)
echo "Directorio temporal: $TEMP_DIR"

# Clonar solo la última versión (sin historial)
git clone --depth 1 . $TEMP_DIR

# Entrar al directorio temporal
cd $TEMP_DIR

# Eliminar la conexión al repositorio original
git remote remove origin

# Asegúrate de que el archivo rc-settings.xml usa la variable de entorno
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

# Actualizar .gitignore
if [ -f .gitignore ]; then
    if ! grep -q "rc-settings.xml" .gitignore; then
        echo "" >> .gitignore
        echo "# Maven configuration files with credentials" >> .gitignore
        echo "**/rc-settings.xml" >> .gitignore
        echo "**/.m2/settings.xml" >> .gitignore
    fi
fi

# Commit
git add -A
git commit -m "Repositorio limpio sin tokens expuestos"

echo ""
echo "======================================================================"
echo "Se ha creado un nuevo repositorio limpio en: $TEMP_DIR"
echo ""
echo "Para utilizarlo, sigue estos pasos:"
echo ""
echo "1. Crea un nuevo repositorio en GitHub (vacío, sin README ni gitignore)"
echo "2. Ejecuta los siguientes comandos:"
echo "   cd $TEMP_DIR"
echo "   git remote add origin https://github.com/tu-usuario/nuevo-repositorio.git"
echo "   git push -u origin main"
echo ""
echo "Nota: Asegúrate de actualizar la URL del repositorio con tu usuario y nombre de repo"
echo "======================================================================"
