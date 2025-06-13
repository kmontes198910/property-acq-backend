-- Modificar la columna digital_cert_password para aumentar su tamaño a 1024 caracteres
ALTER TABLE issuers ALTER COLUMN digital_cert_password TYPE VARCHAR(1024);

-- Modificar la columna digital_cert_p12 para usar TEXT en lugar de VARCHAR(255)
ALTER TABLE issuers ALTER COLUMN digital_cert_p12 TYPE TEXT;
