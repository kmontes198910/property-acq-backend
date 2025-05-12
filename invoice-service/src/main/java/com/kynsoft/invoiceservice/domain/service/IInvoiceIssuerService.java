package com.kynsoft.invoiceservice.domain.service;

import com.kynsoft.invoiceservice.domain.dto.InvoiceIssuerDto;

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
}
