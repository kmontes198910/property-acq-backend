-- Verificar y agregar la columna identification_type a la tabla patients si no existe
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_name = 'patients'
        AND column_name = 'identification_type'
    ) THEN
        ALTER TABLE patient.patients ADD COLUMN identification_type VARCHAR(50);
    END IF;
END$$;

-- Actualizar los registros existentes con valores nulos en identification_type
UPDATE patient.patients
SET identification_type = '05' -- Código para CEDULA como valor por defecto
WHERE identification_type IS NULL;

-- Verificar que no hay más registros con identification_type NULL
SELECT COUNT(*) FROM patient.patients WHERE identification_type IS NULL;

-- Agregar la columna skin_color a la tabla patients si no existe
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_name = 'patients'
        AND column_name = 'skin_color'
    ) THEN
        ALTER TABLE patient.patients ADD COLUMN skin_color VARCHAR(50);
    END IF;
END$$;

-- Establecer un valor por defecto para la columna skin_color en registros existentes
UPDATE patient.patients
SET skin_color = 'NO_ESPECIFICADO' -- Valor por defecto para
WHERE skin_color IS NULL;
