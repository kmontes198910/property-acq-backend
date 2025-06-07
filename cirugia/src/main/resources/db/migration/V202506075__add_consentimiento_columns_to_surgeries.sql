-- Add consentimiento columns to surgeries table
ALTER TABLE surgeries
ADD COLUMN consentimiento_informado_cirugia TEXT,
ADD COLUMN consentimiento_informado_anestesia TEXT;
