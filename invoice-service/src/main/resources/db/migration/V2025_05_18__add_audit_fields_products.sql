-- Añadir campos de auditoría a la tabla products
ALTER TABLE products 
ADD COLUMN created_by UUID,
ADD COLUMN updated_by UUID;

COMMENT ON COLUMN products.created_by IS 'UUID del usuario que creó el registro';
COMMENT ON COLUMN products.updated_by IS 'UUID del usuario que actualizó el registro por última vez';

-- Añadir campos de auditoría a la tabla product_categories
ALTER TABLE product_categories 
ADD COLUMN created_by UUID,
ADD COLUMN updated_by UUID;

COMMENT ON COLUMN product_categories.created_by IS 'UUID del usuario que creó el registro';
COMMENT ON COLUMN product_categories.updated_by IS 'UUID del usuario que actualizó el registro por última vez';
