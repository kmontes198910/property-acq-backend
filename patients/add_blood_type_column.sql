-- Script para añadir la columna bloodType a la tabla patients
ALTER TABLE patients ADD COLUMN IF NOT EXISTS blood_type VARCHAR(255);
ALTER TABLE patients 
  ADD CONSTRAINT chk_patient_blood_type 
  CHECK (blood_type IN ('A_POSITIVE','A_NEGATIVE','B_POSITIVE','B_NEGATIVE',
                        'AB_POSITIVE','AB_NEGATIVE','O_POSITIVE','O_NEGATIVE','UNKNOWN'));

-- Actualizar todos los registros existentes para usar el valor por defecto
UPDATE patients SET blood_type = 'UNKNOWN' WHERE blood_type IS NULL;

-- Nota: Ejecutar este script con un usuario que tenga privilegios de propietario en la tabla
