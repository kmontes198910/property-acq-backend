CREATE TABLE examinations (
    id UUID PRIMARY KEY,
    order_id UUID NOT NULL REFERENCES examination_orders(id),
    examination_type VARCHAR(100) NOT NULL,
    status VARCHAR(50) NOT NULL,
    results TEXT,
    completion_date TIMESTAMP,
    observations TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP,
    created_by UUID,
    updated_by UUID
);

CREATE INDEX idx_examinations_order_id ON examinations(order_id);
CREATE INDEX idx_examinations_type ON examinations(examination_type);
CREATE INDEX idx_examinations_status ON examinations(status);

COMMENT ON TABLE examinations IS 'Tabla que almacena los exámenes médicos';
COMMENT ON COLUMN examinations.id IS 'Identificador único del examen';
COMMENT ON COLUMN examinations.order_id IS 'Identificador de la orden a la que pertenece este examen';
COMMENT ON COLUMN examinations.examination_type IS 'Tipo de examen (código o nombre del examen)';
COMMENT ON COLUMN examinations.status IS 'Estado del examen (PENDIENTE, EN_PROCESO, COMPLETADO, etc.)';
COMMENT ON COLUMN examinations.results IS 'Resultados del examen';
COMMENT ON COLUMN examinations.completion_date IS 'Fecha de finalización del examen';
COMMENT ON COLUMN examinations.observations IS 'Observaciones o notas sobre el examen';
