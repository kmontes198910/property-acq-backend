package com.kynsoft.invoiceservice.domain.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interfaz que define las operaciones de servicio para la entidad Product
 */
public interface IProductService {
    
    /**
     * Realiza una búsqueda avanzada de productos con filtros y paginación
     * 
     * @param pageable Configuración de paginación
     * @param filterCriteria Lista de criterios de filtrado
     * @return Respuesta paginada con los resultados de la búsqueda
     */
    PaginatedResponse searchAdvanced(Pageable pageable, List<FilterCriteria> filterCriteria);
}
