package com.kynsoft.invoiceservice.domain.service;

import com.kynsoft.invoiceservice.application.query.customer.get.CustomerDto;

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

    CustomerDto findByIdentificationNumber(String identificationNumber);
}
    
