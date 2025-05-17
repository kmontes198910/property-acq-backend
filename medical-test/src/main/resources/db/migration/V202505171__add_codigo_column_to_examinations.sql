-- Agregar columna 'code' a la tabla de exámenes
ALTER TABLE examinations ADD COLUMN code VARCHAR(50) NOT NULL DEFAULT '';

-- Agregar índice para facilitar búsquedas por código
CREATE INDEX idx_examinations_code ON examinations(code);

-- Documentar la nueva columna
COMMENT ON COLUMN examinations.code IS 'Unique code of the examination';

-- Actualizar la restricción para que la columna sea obligatoria después de la migración inicial
ALTER TABLE examinations ALTER COLUMN code DROP DEFAULT;
