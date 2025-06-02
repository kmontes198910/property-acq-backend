-- Eliminar columnas no utilizadas de la tabla products
ALTER TABLE products 
DROP COLUMN IF EXISTS rent_code,
DROP COLUMN IF EXISTS rent_tax_percentage,
DROP COLUMN IF EXISTS stock,
DROP COLUMN IF EXISTS is_service;
