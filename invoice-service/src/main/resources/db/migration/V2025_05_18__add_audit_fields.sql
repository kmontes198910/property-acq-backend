-- Añadir campos de auditoría a la tabla customers
ALTER TABLE customers 
ADD COLUMN created_by UUID,
ADD COLUMN updated_by UUID;

COMMENT ON COLUMN customers.created_by IS 'UUID del usuario que creó el registro';
COMMENT ON COLUMN customers.updated_by IS 'UUID del usuario que actualizó el registro por última vez';

-- Añadir campos de auditoría a la tabla invoices
ALTER TABLE invoices 
ADD COLUMN created_by UUID,
ADD COLUMN updated_by UUID;

COMMENT ON COLUMN invoices.created_by IS 'UUID del usuario que creó el registro';
COMMENT ON COLUMN invoices.updated_by IS 'UUID del usuario que actualizó el registro por última vez';
