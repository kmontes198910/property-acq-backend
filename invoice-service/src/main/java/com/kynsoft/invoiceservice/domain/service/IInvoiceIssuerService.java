package com.kynsoft.invoiceservice.domain.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.invoiceservice.domain.dto.InvoiceIssuerDto;
import com.kynsoft.invoiceservice.infrastructure.entities.InvoiceIssuer;

import java.util.List;
import java.util.UUID;

/**
 * Interfaz para el servicio de gestión de emisores de facturas
 */
public interface IInvoiceIssuerService {
    
    /**
     * Obtiene un emisor de facturas por su ID
     * 
     * @param id ID del emisor
     * @return DTO con los datos del emisor de facturas
     * @throws com.kynsoft.invoiceservice.domain.exception.BusinessInvoiceException si no se encuentra el emisor
     */
    InvoiceIssuerDto getById(UUID id);
    
    /**
     * Obtiene un emisor de facturas por su RUC
     * 
     * @param ruc RUC del emisor
     * @return DTO con los datos del emisor de facturas
     * @throws com.kynsoft.invoiceservice.domain.exception.BusinessInvoiceException si no se encuentra el emisor
     */
    InvoiceIssuerDto getByRuc(String ruc);
    
    /**
     * Obtiene el emisor de facturas activo/predeterminado
     * 
     * @return DTO con los datos del emisor de facturas activo
     * @throws com.kynsoft.invoiceservice.domain.exception.BusinessInvoiceException si no se encuentra un emisor activo
     */
    InvoiceIssuerDto getActiveIssuer();
    
    /**
     * Obtiene todos los emisores de facturas
     * 
     * @return Lista de DTOs con los datos de los emisores de facturas
     */
    List<InvoiceIssuerDto> getAll();
    
    /**
     * Realiza una búsqueda avanzada de emisores de facturas con filtros y paginación
     * 
     * @param pageable Configuración de paginación
     * @param filterCriteria Lista de criterios de filtrado
     * @return Respuesta paginada con los resultados de la búsqueda
     */
 PaginatedResponse search(org.springframework.data.domain.Pageable pageable,
                          List<FilterCriteria> filterCriteria);

    InvoiceIssuer create(InvoiceIssuer issuer);
}
