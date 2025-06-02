package com.kynsoft.invoiceservice.infrastructure.repository.query;

import com.kynsoft.invoiceservice.infrastructure.entities.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio para la gestión de impuestos
 */
@Repository
public interface TaxRepository extends JpaRepository<Tax, UUID> {

    /**
     * Busca un impuesto por su código
     * 
     * @param code Código del impuesto
     * @return Impuesto encontrado (opcional)
     */
    Optional<Tax> findByCode(String code);
    
    /**
     * Busca impuestos por su tipo
     * 
     * @param taxType Tipo de impuesto (RETENCION, IVA, ICE)
     * @return Lista de impuestos del tipo especificado
     */
    List<Tax> findByTaxType(Tax.TaxType taxType);
    
    /**
     * Busca impuestos activos
     * 
     * @return Lista de impuestos activos
     */
    List<Tax> findByStatusTrue();
    
    /**
     * Busca impuestos activos de un tipo específico
     * 
     * @param taxType Tipo de impuesto (RETENCION, IVA, ICE)
     * @return Lista de impuestos activos del tipo especificado
     */
    List<Tax> findByTaxTypeAndStatusTrue(Tax.TaxType taxType);
}
