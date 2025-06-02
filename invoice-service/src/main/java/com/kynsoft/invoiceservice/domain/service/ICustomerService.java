package com.kynsoft.invoiceservice.domain.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.invoiceservice.application.query.customer.get.CustomerDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Interfaz que define las operaciones de servicio para la entidad Customer
 */
public interface ICustomerService {
    /**
     * Crea un nuevo cliente
     *
     * @param customer Datos del cliente a crear
     * @return ID del cliente creado
     */
    UUID create(CustomerDto customer);

    /**
     * Actualiza un cliente existente
     *
     * @param customer Datos actualizados del cliente
     */
    void update(CustomerDto customer);

    /**
     * Elimina un cliente (marca como inactivo)
     *
     * @param id ID del cliente a eliminar
     */
    void delete(UUID id);

    /**
     * Busca un cliente por su ID
     *
     * @param id ID del cliente a buscar
     * @return Datos del cliente encontrado
     */
    CustomerDto findById(UUID id);

    /**
     * Busca un cliente por su número de identificación
     * 
     * @param identificationNumber Número de identificación del cliente
     * @return Datos del cliente encontrado
     */
    CustomerDto findByIdentificationNumber(String identificationNumber);
    
    /**
     * Realiza una búsqueda avanzada con filtros y paginación
     * 
     * @param pageable Configuración de paginación
     * @param filterCriteria Lista de criterios de filtrado
     * @return Respuesta paginada con los resultados de la búsqueda
     */
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}

