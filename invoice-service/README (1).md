
# Librería E-Facturacion

## Instalación de la librería

### 1. Configuración inicial

**Crea una carpeta `repository`** en la raíz de tu proyecto donde se almacenará la librería localmente.

```bash
mkdir repository
```
### 2. Instalación del archivo JAR

Ejecuta el siguiente comando Maven, **ajustando la ruta del archivo JAR** según corresponda a tu entorno:

```bash
mvn install:install-file \
  -Dfile=/Users/keimermontes/Development/medinec/scheduled/invoice-service/libs/E-Facturacion-1.0.0.jar \
  -DgroupId=ec.e.facturacion \
  -DartifactId=E-Facturacion \
  -Dversion=1.0.0 \
  -Dpackaging=jar \
  -Dclassifier=1.1 \
  -DlocalRepositoryPath=repository \
  -DcreateChecksum=true
```

#### Nota importante

**Reemplaza** `/ruta/completa/al/proyecto/` con la ruta absoluta donde se encuentra tu archivo JAR generado.

### 3. Configuración del pom.xml

Agrega el siguiente repositorio local en tu archivo `pom.xml`:

```xml
<repositories>
    <repository>
        <id>project.local</id>
        <name>project</name>
        <url>file://${project.basedir}/repository</url>
        <releases>
            <enabled>true</enabled>
            <checksumPolicy>ignore</checksumPolicy>
        </releases>
    </repository>
</repositories>
```

Incluye esta dependencia en la sección `<dependencies>` de tu `pom.xml`:

```xml
<dependency>
    <groupId>ec.e.facturacion</groupId>
    <artifactId>E-Facturacion</artifactId>
    <version>1.0.0</version>
</dependency>
```