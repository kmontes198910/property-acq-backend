-- Script para otorgar permisos al usuario de la base de datos para acceder al esquema medical-test
-- Reemplaza 'usuario_db' con el nombre de usuario real que está utilizando la aplicación

-- Crear esquema si no existe
CREATE SCHEMA IF NOT EXISTS "medical-test";

-- Otorgar todos los permisos sobre el esquema al usuario
GRANT ALL ON SCHEMA "medical-test" TO usuario_db;

-- Otorgar todos los permisos sobre todas las tablas futuras en el esquema
ALTER DEFAULT PRIVILEGES IN SCHEMA "medical-test" GRANT ALL ON TABLES TO usuario_db;

-- Otorgar permisos sobre todas las tablas existentes en el esquema
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA "medical-test" TO usuario_db;

-- Otorgar permisos sobre todas las secuencias en el esquema
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA "medical-test" TO usuario_db;

-- Otorgar permisos sobre todas las funciones en el esquema
GRANT ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA "medical-test" TO usuario_db;
