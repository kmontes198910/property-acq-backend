-- Modificar la columna surgery_id para hacerla opcional
ALTER TABLE treatments ALTER COLUMN surgery_id DROP NOT NULL;

-- Agregar la columna patient_id
ALTER TABLE treatments ADD COLUMN patient_id UUID NOT NULL;

-- Agregar comentario para facilitar seguimiento de cambios
COMMENT ON COLUMN treatments.patient_id IS 'ID del paciente asociado al tratamiento';
