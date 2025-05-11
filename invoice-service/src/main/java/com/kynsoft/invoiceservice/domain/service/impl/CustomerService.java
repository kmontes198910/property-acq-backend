package com.kynsoft.invoiceservice.domain.service.impl;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.invoiceservice.application.query.customer.get.CustomerDto;
import com.kynsoft.invoiceservice.domain.exception.BusinessInvoiceException;
import com.kynsoft.invoiceservice.domain.exception.DomainErrorInvoiceMessage;
import com.kynsoft.invoiceservice.domain.service.ICustomerService;
import com.kynsoft.invoiceservice.infrastructure.entities.Customer;
import com.kynsoft.invoiceservice.infrastructure.repository.command.CustomerWriteRepository;
import com.kynsoft.invoiceservice.infrastructure.repository.query.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerWriteRepository customerWriteRepository;

    @Override
    public UUID create(CustomerDto customerDto) {
        log.info("Creando nuevo cliente: {}", customerDto.getBusinessName());
        
        // Validar que los campos obligatorios estén presentes
        if (customerDto.getIdentificationType() == null || 
            customerDto.getIdentificationNumber() == null || 
            customerDto.getBusinessName() == null) {
            throw new BusinessInvoiceException(DomainErrorInvoiceMessage.INVALID_INVOICE_DATA, 
                    "Los campos tipo de identificación, número de identificación y nombre comercial son obligatorios");
        }
        
        // Verificar si ya existe un cliente con el mismo número de identificación
        customerRepository.findByIdNumber(customerDto.getIdentificationNumber())
            .ifPresent(customer -> {
                throw new BusinessInvoiceException(DomainErrorInvoiceMessage.ALREADY_EXISTS, 
                    "Ya existe un cliente con el número de identificación: " + customerDto.getIdentificationNumber());
            });
        
        // Asignar un ID si no tiene uno
        if (customerDto.getId() == null) {
            customerDto.setId(UUID.randomUUID());
        }
        
        // Asegurar que el cliente esté activo por defecto si no se especifica
        if (customerDto.getIsActive() == null) {
            customerDto.setIsActive(true);
        }
        
        // Crear la entidad Customer y guardarla
        Customer customer = Customer.builder()
                .id(customerDto.getId())
                .idType(customerDto.getIdentificationType())
                .idNumber(customerDto.getIdentificationNumber())
                .businessName(customerDto.getBusinessName())
                .address(customerDto.getAddress())
                .email(customerDto.getEmail())
                .phone(customerDto.getPhoneNumber())
                .isActive(customerDto.getIsActive())
                .createdAt(LocalDateTime.now())
                .build();
        
        Customer savedCustomer = customerWriteRepository.save(customer);
        log.info("Cliente creado con ID: {}", savedCustomer.getId());
        
        return savedCustomer.getId();
    }

    @Override
    public void update(CustomerDto customerDto) {
        log.info("Actualizando cliente con ID: {}", customerDto.getId());
        
        // Validar que el ID del cliente no sea nulo
        if (customerDto.getId() == null) {
            throw new BusinessInvoiceException(DomainErrorInvoiceMessage.CUSTOMER_NOT_FOUND, 
                    "El ID del cliente no puede ser nulo para una actualización");
        }
        
        // Verificar que el cliente exista
        Customer existingCustomer = customerRepository.findById(customerDto.getId())
                .orElseThrow(() -> new BusinessInvoiceException(DomainErrorInvoiceMessage.CUSTOMER_NOT_FOUND, 
                        "Cliente no encontrado con ID: " + customerDto.getId()));
        
        // Verificar si se está cambiando el número de identificación y si el nuevo ya existe
        if (customerDto.getIdentificationNumber() != null && 
            !existingCustomer.getIdNumber().equals(customerDto.getIdentificationNumber())) {
            customerRepository.findByIdNumber(customerDto.getIdentificationNumber())
                .ifPresent(customer -> {
                    if (!customer.getId().equals(customerDto.getId())) {
                        throw new BusinessInvoiceException(DomainErrorInvoiceMessage.ALREADY_EXISTS, 
                            "Ya existe un cliente con el número de identificación: " + customerDto.getIdentificationNumber());
                    }
                });
        }
        
        // Actualizar los campos del cliente
        if (customerDto.getIdentificationType() != null) {
            existingCustomer.setIdType(customerDto.getIdentificationType());
        }
        
        if (customerDto.getIdentificationNumber() != null) {
            existingCustomer.setIdNumber(customerDto.getIdentificationNumber());
        }
        
        if (customerDto.getBusinessName() != null) {
            existingCustomer.setBusinessName(customerDto.getBusinessName());
        }
        
        if (customerDto.getAddress() != null) {
            existingCustomer.setAddress(customerDto.getAddress());
        }
        
        if (customerDto.getEmail() != null) {
            existingCustomer.setEmail(customerDto.getEmail());
        }
        
        if (customerDto.getPhoneNumber() != null) {
            existingCustomer.setPhone(customerDto.getPhoneNumber());
        }
        
        if (customerDto.getIsActive() != null) {
            existingCustomer.setIsActive(customerDto.getIsActive());
        }
        
        // Actualizar timestamp
        existingCustomer.setUpdatedAt(LocalDateTime.now());
        
        // Guardar los cambios
        customerWriteRepository.save(existingCustomer);
        log.info("Cliente actualizado correctamente, ID: {}", existingCustomer.getId());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(UUID id) {
        log.info("Eliminando cliente con ID: {}", id);
        
        // Verificar que el cliente exista
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new BusinessInvoiceException(DomainErrorInvoiceMessage.CUSTOMER_NOT_FOUND, 
                        "Cliente no encontrado con ID: " + id));
        
        // Si se requiere eliminación lógica (soft delete)
        customer.setIsActive(false);
        customer.setUpdatedAt(LocalDateTime.now());
        customerWriteRepository.save(customer);
        log.info("Cliente desactivado correctamente, ID: {}", id);
        
        // Si se requiere eliminación física (hard delete)
        // customerWriteRepository.deleteById(id);
        // log.info("Cliente eliminado físicamente, ID: {}", id);
    }

    @Override
    public CustomerDto findById(UUID id) {
        log.info("Buscando cliente con ID: {}", id);
        
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new BusinessInvoiceException(DomainErrorInvoiceMessage.CUSTOMER_NOT_FOUND, 
                        "Cliente no encontrado con ID: " + id));
        
        return mapEntityToDto(customer);
    }

    @Override
    public CustomerDto findByIdentificationNumber(String identificationNumber) {
        log.info("Buscando cliente con número de identificación: {}", identificationNumber);
        
        // Validar que el número de identificación no sea nulo o vacío
        if (identificationNumber == null || identificationNumber.trim().isEmpty()) {
            throw new BusinessInvoiceException(DomainErrorInvoiceMessage.INVALID_INVOICE_DATA, 
                    "El número de identificación no puede ser nulo o vacío");
        }
        
        // Buscar el cliente en el repositorio
        Customer customer = customerRepository.findByIdNumber(identificationNumber)
                .orElseThrow(() -> new BusinessInvoiceException(DomainErrorInvoiceMessage.CUSTOMER_NOT_FOUND, 
                        "Cliente no encontrado con número de identificación: " + identificationNumber));
        
        return mapEntityToDto(customer);
    }
    
    private CustomerDto mapEntityToDto(Customer customer) {
        return CustomerDto.builder()
                .id(customer.getId())
                .identificationType(customer.getIdType())
                .identificationNumber(customer.getIdNumber())
                .businessName(customer.getBusinessName())
                .address(customer.getAddress())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhone())
                .isActive(customer.getIsActive())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }
}