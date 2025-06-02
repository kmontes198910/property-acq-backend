package com.kynsoft.invoiceservice.domain.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.invoiceservice.infrastructure.entities.Tax;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interfaz que define las operaciones de servicio para la entidad Tax
 */
public interface ITaxService {
    
    /**
     * Realiza una búsqueda avanzada de impuestos con filtros y paginación
     * 
     * @param pageable Configuración de paginación
     * @param filterCriteria Lista de criterios de filtrado
     * @return Respuesta paginada con los resultados de la búsqueda
     */
    PaginatedResponse searchAdvanced(Pageable pageable, List<FilterCriteria> filterCriteria);
    
    /**
     * Busca impuestos por tipo
     * 
     * @param type Tipo de impuesto (RETENCION, IVA, ICE)
     * @param pageable Configuración de paginación
     * @return Respuesta paginada con los resultados de la búsqueda
     */
    PaginatedResponse findByTaxType(Tax.TaxType type, Pageable pageable);
    
    /**
     * Elimina un impuesto por su ID (eliminación lógica)
     *
     * @param id ID del impuesto a eliminar
     */
    void delete(UUID id);

    /**
     * Busca un impuesto por su código
     *
     * @param code Código del impuesto
     * @return Optional con el impuesto encontrado
     */
    Optional<Tax> findByCode(String code);

    /**
     * Crea un nuevo impuesto
     *
     * @param tax Entidad impuesto a crear
     * @return Impuesto creado
     */
    Tax create(Tax tax);

    /**
     * Busca un impuesto por su ID
     *
     * @param taxId ID del impuesto
     * @return Optional con el impuesto encontrado
     */
    Optional<Tax> findById(UUID taxId);

    /**
     * Actualiza un impuesto existente
     *
     * @param tax Impuesto con los datos actualizados
     */
    void update(Tax tax);
}
