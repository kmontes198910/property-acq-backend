package com.kynsoft.invoiceservice.domain.service;


import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.invoiceservice.domain.dto.InvoiceDto;
import com.kynsoft.invoiceservice.infrastructure.entities.InvoiceStatus;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

/**
 * Interfaz que define las operaciones de servicio para la entidad Invoice
 */
public interface IInvoiceService {
    /**
     * Crea una nueva factura
     *
     * @param invoice Datos de la factura a crear
     * @return ID de la factura creada
     */
    UUID create(InvoiceDto invoice);

    /**
     * Actualiza una factura existente
     *
     * @param invoice Datos actualizados de la factura
     */
    void update(InvoiceDto invoice);

    /**
     * Busca una factura por su ID
     *
     * @param id ID de la factura a buscar
     * @return Datos de la factura encontrada
     */
    InvoiceDto findById(UUID id);
    
    /**
     * Busca facturas por diversos criterios
     *
     * @param customerId ID del cliente (opcional)
     * @param documentNumber Número de documento (opcional)
     * @param status Estado de la factura (opcional)
     * @return Lista de facturas que cumplen con los criterios de búsqueda
     */
    List<InvoiceDto> search(UUID customerId, String documentNumber, InvoiceStatus status);
    
    /**
     * Cambia el estado de una factura
     *
     * @param id ID de la factura
     * @param status Nuevo estado de la factura
     * @return Datos actualizados de la factura
     */
    InvoiceDto changeStatus(UUID id, InvoiceStatus status);
    
    /**
     * Realiza una búsqueda avanzada con filtros y paginación
     * 
     * @param pageable Configuración de paginación
     * @param filterCriteria Lista de criterios de filtrado
     * @return Respuesta paginada con los resultados de la búsqueda
     */
    PaginatedResponse searchAdvanced(Pageable pageable, List<FilterCriteria> filterCriteria);
}
