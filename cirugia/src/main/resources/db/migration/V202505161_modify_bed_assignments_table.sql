-- Eliminar restricciones NOT NULL para los campos de cirugía en tabla bed_assignments
ALTER TABLE bed_assignments ALTER COLUMN surgery_id DROP NOT NULL;
ALTER TABLE bed_assignments ALTER COLUMN surgery_stage DROP NOT NULL;
