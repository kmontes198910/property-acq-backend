CREATE TABLE examination_orders (
    id UUID PRIMARY KEY,
    patient_id UUID NOT NULL,
    doctor_id UUID NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL,
    observations TEXT,
    business_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    created_by UUID,
    updated_by UUID
);

COMMENT ON TABLE examination_orders IS 'Tabla que almacena las órdenes de exámenes médicos';
COMMENT ON COLUMN examination_orders.id IS 'Identificador único de la orden';
COMMENT ON COLUMN examination_orders.patient_id IS 'Identificador del paciente';
COMMENT ON COLUMN examination_orders.doctor_id IS 'Identificador del médico que ordenó los exámenes';
COMMENT ON COLUMN examination_orders.creation_date IS 'Fecha de creación de la orden';
COMMENT ON COLUMN examination_orders.status IS 'Estado de la orden (PENDIENTE, EN_PROCESO, COMPLETADA, etc.)';
COMMENT ON COLUMN examination_orders.observations IS 'Observaciones o notas adicionales';
COMMENT ON COLUMN examination_orders.business_id IS 'ID del negocio/sucursal donde se procesa la orden';
