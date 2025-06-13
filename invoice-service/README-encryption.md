# Encriptación de Datos Sensibles en Certificados Digitales

## Descripción

Este documento describe la implementación del mecanismo de encriptación para los datos sensibles relacionados con los certificados digitales en la entidad `Issuer`, que incluye tanto la contraseña del certificado como los datos del certificado digital P12.

## Arquitectura de la Solución

La solución se basa en los siguientes componentes:

1. **AttributeEncryptor**: Convertidor JPA para encriptar/desencriptar automáticamente los campos anotados.
2. **EncryptionUtil**: Utilidad que maneja la lógica de encriptación y desencriptación.
3. **CredentialUtil**: Utilidad específica para manejar credenciales sensibles, asegurando que estén correctamente encriptadas/desencriptadas.
4. **EncryptionConfig**: Configuración para cargar propiedades de encriptación desde un archivo externo.
5. **SensitiveDataMigrationService**: Servicio para migrar datos sensibles existentes al nuevo formato encriptado.
6. **IssuerCredentialsService**: Servicio para operaciones específicas relacionadas con las credenciales del emisor.

## Tecnología Utilizada

- **Spring Security Crypto**: Biblioteca para encriptación utilizando algoritmo AES-256 con PBKDF2.
- **JPA Attribute Converter**: Mecanismo de JPA para transformaciones automáticas de atributos.

## Funcionamiento

1. Cuando se guarda una entidad `Issuer`:
   - El atributo `digitalCertPassword` se encripta automáticamente
   - El atributo `digitalCertP12` (certificado digital en formato P12) se encripta automáticamente
2. Cuando se lee una entidad `Issuer`:
   - El atributo `digitalCertPassword` se desencripta automáticamente
   - El atributo `digitalCertP12` se desencripta automáticamente
3. En la base de datos, tanto las contraseñas como los certificados digitales aparecen como texto encriptado.

## Seguridad

- El algoritmo utilizado es AES-256 con una función de derivación de clave PBKDF2.
- Las claves de encriptación se pueden configurar como variables de entorno para mayor seguridad.
- Las claves predeterminadas deben cambiarse en entornos de producción.

## Variables de Entorno

Para mayor seguridad en producción, configure las siguientes variables de entorno:

- `ENCRYPTION_SECRET_KEY`: Clave secreta para encriptación (32 caracteres recomendados).
- `ENCRYPTION_SALT`: Sal para encriptación (16 caracteres hexadecimales).

## Consideraciones para Producción

1. **IMPORTANTE**: Cambiar las claves de encriptación por defecto.
2. Usar variables de entorno en lugar de archivos de propiedades.
3. Asegurar que las claves de encriptación estén respaldadas en un lugar seguro.
4. Ejecutar pruebas exhaustivas antes de implementar en producción.
5. Considerar implementar una bóveda de secretos (como HashiCorp Vault o AWS Secrets Manager) para almacenar las claves de encriptación.

## Migración de Datos

El sistema incluye un servicio de migración (`SensitiveDataMigrationService`) que se ejecuta automáticamente al iniciar la aplicación. Este servicio:

1. Busca todos los registros de emisores en la base de datos
2. Identifica campos sensibles no encriptados
3. Encripta automáticamente estos campos
4. Guarda los registros actualizados

## Solución de Problemas

### Detección de Datos Encriptados

El sistema utiliza un mecanismo mejorado para detectar si los datos ya están encriptados:

1. Verifica el formato de los datos (patrón hexadecimal)
2. Intenta desencriptarlos como verificación secundaria

### Manejo de Datos no Encriptados

Cuando se encuentran datos no encriptados en la base de datos:

1. El convertidor `AttributeEncryptor` los detecta automáticamente
2. Los devuelve sin modificaciones para evitar errores
3. El servicio de migración los encripta cuando corresponde

### Errores Comunes

1. **Error "Non-hex character"**: 
   - Causa: Intentar desencriptar datos que no están en formato encriptado
   - Solución: Implementación mejorada de `isEncrypted()` para detectar correctamente el formato

2. **Error "Valor demasiado largo para tipo VARCHAR"**:
   - Causa: El texto encriptado es más largo que el límite de la columna en la base de datos
   - Solución: Migración de base de datos para ampliar el tamaño de la columna (V2__increase_cert_column_size.sql)

3. **Error "ConflictingBeanDefinitionException"**:
   - Causa: Múltiples definiciones de bean con el mismo nombre
   - Solución: Eliminar archivos duplicados o resolver conflictos de nombres

4. **Error al procesar certificados digitales**:
   - Causa: La contraseña del certificado digital puede no estar siendo correctamente desencriptada
   - Solución: Implementación de `CredentialUtil` para asegurar que las contraseñas siempre estén desencriptadas cuando se necesitan
6. Limitar el acceso a los endpoints de gestión de certificados y contraseñas solo a usuarios administradores autorizados.

## Consideraciones de Seguridad para Certificados Digitales

1. Los certificados digitales P12 contienen claves privadas y son altamente sensibles.
2. Además del cifrado en base de datos, se recomienda:
   - Implementar auditoría de acceso a los certificados
   - Configurar rotación periódica de certificados
   - Considerar el uso de HSM (Hardware Security Modules) en entornos de alta seguridad
   - Aplicar el principio de mínimo privilegio para el acceso a los certificados
3. Mantener copias de seguridad cifradas de los certificados en ubicaciones seguras.
4. Implementar procesos de renovación de certificados antes de su expiración.

## API REST para Gestión de Credenciales

Se ha implementado un controlador REST para facilitar la gestión de las credenciales del emisor:

### Gestión de Contraseñas
- `POST /api/v1/issuer-credentials/{issuerId}/update-password`: Actualiza la contraseña del certificado digital.
- `POST /api/v1/issuer-credentials/{issuerId}/verify-password`: Verifica si una contraseña es válida para el certificado.

### Gestión de Certificados Digitales
- `POST /api/v1/issuer-credentials/{issuerId}/update-certificate`: Actualiza el certificado digital P12 del emisor.
- `GET /api/v1/issuer-credentials/{issuerId}/certificate`: Obtiene el certificado digital P12 del emisor.

## Migración de Datos Existentes

Al iniciar la aplicación, el `SensitiveDataMigrationService` se ejecuta automáticamente para encriptar los datos sensibles existentes almacenados en texto plano, tanto contraseñas como certificados digitales.

## Manejo de Datos No Encriptados

El sistema está diseñado para gestionar eficazmente la transición de datos no encriptados a encriptados:

1. El método `isEncrypted()` en `EncryptionUtil` verifica el formato de los datos para determinar si ya están encriptados.
2. El `AttributeEncryptor` detecta automáticamente si los datos leídos de la base de datos ya están encriptados:
   - Si están encriptados, los desencripta
   - Si no están encriptados, los devuelve sin modificar
3. El `SensitiveDataMigrationService` encripta los datos no encriptados de manera robusta con manejo de excepciones.

Esta implementación robusta garantiza que la aplicación pueda arrancar correctamente incluso cuando hay una mezcla de datos encriptados y no encriptados en la base de datos, facilitando una migración sin interrupciones.
