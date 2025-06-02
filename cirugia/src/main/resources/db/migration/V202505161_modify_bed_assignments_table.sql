-- Eliminar restricción NOT NULL para el campo surgery_id en tabla bed_assignments
ALTER TABLE bed_assignments ALTER COLUMN surgery_id DROP NOT NULL;
