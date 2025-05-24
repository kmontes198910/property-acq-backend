package com.kynsoft.invoiceservice.infrastructure.repository.query;

import com.kynsoft.invoiceservice.infrastructure.entities.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaxReadRepository extends JpaRepository<Tax, UUID>, JpaSpecificationExecutor<Tax> {
    
    /**
     * Busca un impuesto por su código
     * @param code Código del impuesto
     * @return Optional con el impuesto encontrado
     */
    Optional<Tax> findByCode(String code);
    
    /**
     * Busca impuestos por tipo
     * @param taxType Tipo de impuesto (RETENCION, IVA, ICE)
     * @return Lista de impuestos del tipo especificado
     */
    List<Tax> findByTaxType(Tax.TaxType taxType);
    
    /**
     * Busca impuestos activos
     * @return Lista de impuestos activos
     */
    List<Tax> findByStatusTrue();
    
    /**
     * Busca impuestos activos de un tipo específico
     * @param taxType Tipo de impuesto (RETENCION, IVA, ICE)
     * @return Lista de impuestos activos del tipo especificado
     */
    List<Tax> findByTaxTypeAndStatusTrue(Tax.TaxType taxType);
}
