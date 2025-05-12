package com.kynsoft.invoiceservice.domain.service.impl;

import com.kynsoft.invoiceservice.application.query.customer.get.CustomerDto;
import com.kynsoft.invoiceservice.domain.dto.*;
import com.kynsoft.invoiceservice.domain.exception.BusinessInvoiceException;
import com.kynsoft.invoiceservice.domain.exception.DomainErrorInvoiceMessage;
import com.kynsoft.invoiceservice.domain.service.ICustomerService;
import com.kynsoft.invoiceservice.domain.service.IInvoiceService;
import com.kynsoft.invoiceservice.dto.InvoiceIssuerDTO;
import com.kynsoft.invoiceservice.infrastructure.entities.*;
import com.kynsoft.invoiceservice.infrastructure.repository.query.CustomerReadRepository;
import com.kynsoft.invoiceservice.infrastructure.repository.query.InvoiceIssuerRepository;
import com.kynsoft.invoiceservice.infrastructure.repository.query.InvoiceReadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import com.kynsoft.invoiceservice.infrastructure.repository.command.InvoiceWriteRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceService implements IInvoiceService {

    private final InvoiceReadRepository invoiceRepository;
    private final InvoiceWriteRepository invoiceWriteRepository;
    private final CustomerReadRepository customerRepository;
    private final InvoiceIssuerRepository invoiceIssuerRepository;
    private final ICustomerService customerService;

    @Override
    @Transactional
    public UUID create(InvoiceDto invoiceDto) {
        log.info("Creando nueva factura");
        
        // Validar datos obligatorios
        validateRequiredFields(invoiceDto);
        
        // Generar número secuencial si no existe
        if (invoiceDto.getSequential() == null || invoiceDto.getSequential().isEmpty()) {
            invoiceDto.setSequential(generateNextSequential());
        }
        
        // Asignar fecha de emisión si no existe
        if (invoiceDto.getEmissionDate() == null) {
            invoiceDto.setEmissionDate(LocalDateTime.now());
        }
        
        // Asignar ID si no tiene uno
        if (invoiceDto.getId() == null) {
            invoiceDto.setId(UUID.randomUUID());
        }
        
        // Verificar que el emisor exista
        InvoiceIssuer issuer = getInvoiceIssuer(invoiceDto);
        
        // Verificar que el cliente exista
        Customer customer = getCustomer(invoiceDto);
        
        // Construir la entidad Invoice
        Invoice invoice = Invoice.builder()
                .id(invoiceDto.getId())
                .documentNumber(invoiceDto.getDocumentNumber())
                .sequential(invoiceDto.getSequential())
                .accessKey(invoiceDto.getAccessKey())
                .emissionDate(invoiceDto.getEmissionDate())
                .authorizationDate(invoiceDto.getAuthorizationDate())
                .authorizationNumber(invoiceDto.getAuthorizationNumber())
                .subtotal(invoiceDto.getSubtotal())
                .discount(invoiceDto.getDiscount())
                .taxAmount(invoiceDto.getTaxAmount())
                .totalAmount(invoiceDto.getTotalAmount())
                .tip(invoiceDto.getTip())
                .status(invoiceDto.getStatus() != null ? invoiceDto.getStatus() : InvoiceStatus.DRAFT)
                .remissionGuide(invoiceDto.getRemissionGuide())
                .issuer(issuer)
                .customer(customer)
                .build();
        
        // Procesar detalles de la factura
        if (invoiceDto.getDetails() != null && !invoiceDto.getDetails().isEmpty()) {
            for (InvoiceDetailDto detailDto : invoiceDto.getDetails()) {
                InvoiceDetail detail = mapInvoiceDetailDtoToEntity(detailDto);
                invoice.addDetail(detail);
            }
        }
        
        // Procesar pagos de la factura
        if (invoiceDto.getPayments() != null && !invoiceDto.getPayments().isEmpty()) {
            for (InvoicePaymentDto paymentDto : invoiceDto.getPayments()) {
                InvoicePayment payment = mapInvoicePaymentDtoToEntity(paymentDto);
                invoice.addPayment(payment);
            }
        }
        
        // Procesar campos adicionales
        if (invoiceDto.getAdditionalFields() != null && !invoiceDto.getAdditionalFields().isEmpty()) {
            for (InvoiceAdditionalFieldDto fieldDto : invoiceDto.getAdditionalFields()) {
                InvoiceAdditionalField field = mapInvoiceAdditionalFieldDtoToEntity(fieldDto);
                invoice.addAdditionalField(field);
            }
        }
        
        // Procesar impuestos
        if (invoiceDto.getTaxes() != null && !invoiceDto.getTaxes().isEmpty()) {
            for (InvoiceTaxDto taxDto : invoiceDto.getTaxes()) {
                InvoiceTax tax = mapInvoiceTaxDtoToEntity(taxDto);
                invoice.addTax(tax);
            }
        }
        
        // Guardar la factura
        Invoice savedInvoice = invoiceWriteRepository.save(invoice);
        log.info("Factura creada con ID: {}", savedInvoice.getId());
        
        return savedInvoice.getId();
    }

    @Override
    @Transactional
    public void update(InvoiceDto invoiceDto) {
        log.info("Actualizando factura con ID: {}", invoiceDto.getId());
        
        // Validar que el ID de la factura no sea nulo
        if (invoiceDto.getId() == null) {
            throw new BusinessInvoiceException(DomainErrorInvoiceMessage.INVALID_INVOICE_DATA, 
                    "El ID de la factura no puede ser nulo para una actualización");
        }
        
        // Verificar que la factura exista
        Invoice existingInvoice = invoiceRepository.findById(invoiceDto.getId())
                .orElseThrow(() -> new BusinessInvoiceException(DomainErrorInvoiceMessage.INVOICE_NOT_FOUND, 
                        "Factura no encontrada con ID: " + invoiceDto.getId()));
        
        // Verificar que la factura no esté en un estado que no permita edición
        if (existingInvoice.getStatus() == InvoiceStatus.AUTHORIZED || 
            existingInvoice.getStatus() == InvoiceStatus.ANNULLED) {
            throw new BusinessInvoiceException(DomainErrorInvoiceMessage.INVALID_INVOICE_STATUS, 
                    "No se puede editar una factura con estado " + existingInvoice.getStatus());
        }
        
        // Actualizar campos básicos
        if (invoiceDto.getDocumentNumber() != null) {
            existingInvoice.setDocumentNumber(invoiceDto.getDocumentNumber());
        }
        
        if (invoiceDto.getSequential() != null) {
            existingInvoice.setSequential(invoiceDto.getSequential());
        }
        
        if (invoiceDto.getAccessKey() != null) {
            existingInvoice.setAccessKey(invoiceDto.getAccessKey());
        }
        
        if (invoiceDto.getEmissionDate() != null) {
            existingInvoice.setEmissionDate(invoiceDto.getEmissionDate());
        }
        
        if (invoiceDto.getAuthorizationDate() != null) {
            existingInvoice.setAuthorizationDate(invoiceDto.getAuthorizationDate());
        }
        
        if (invoiceDto.getAuthorizationNumber() != null) {
            existingInvoice.setAuthorizationNumber(invoiceDto.getAuthorizationNumber());
        }
        
        if (invoiceDto.getSubtotal() != null) {
            existingInvoice.setSubtotal(invoiceDto.getSubtotal());
        }
        
        if (invoiceDto.getDiscount() != null) {
            existingInvoice.setDiscount(invoiceDto.getDiscount());
        }
        
        if (invoiceDto.getTaxAmount() != null) {
            existingInvoice.setTaxAmount(invoiceDto.getTaxAmount());
        }
        
        if (invoiceDto.getTotalAmount() != null) {
            existingInvoice.setTotalAmount(invoiceDto.getTotalAmount());
        }
        
        if (invoiceDto.getTip() != null) {
            existingInvoice.setTip(invoiceDto.getTip());
        }
        
        if (invoiceDto.getRemissionGuide() != null) {
            existingInvoice.setRemissionGuide(invoiceDto.getRemissionGuide());
        }
        
        // Actualizar relaciones si es necesario
        if (invoiceDto.getIssuer() != null && invoiceDto.getIssuer().getId() != null) {
            InvoiceIssuer issuer = getInvoiceIssuer(invoiceDto);
            existingInvoice.setIssuer(issuer);
        }
        
        if (invoiceDto.getCustomer() != null && invoiceDto.getCustomer().getId() != null) {
            Customer customer = getCustomer(invoiceDto);
            existingInvoice.setCustomer(customer);
        }
        
        // Actualizar detalles si es necesario
        if (invoiceDto.getDetails() != null && !invoiceDto.getDetails().isEmpty()) {
            // Eliminar detalles actuales
            existingInvoice.getDetails().clear();
            
            // Agregar nuevos detalles
            for (InvoiceDetailDto detailDto : invoiceDto.getDetails()) {
                InvoiceDetail detail = mapInvoiceDetailDtoToEntity(detailDto);
                existingInvoice.addDetail(detail);
            }
        }
        
        // Actualizar pagos si es necesario
        if (invoiceDto.getPayments() != null && !invoiceDto.getPayments().isEmpty()) {
            // Eliminar pagos actuales
            existingInvoice.getPayments().clear();
            
            // Agregar nuevos pagos
            for (InvoicePaymentDto paymentDto : invoiceDto.getPayments()) {
                InvoicePayment payment = mapInvoicePaymentDtoToEntity(paymentDto);
                existingInvoice.addPayment(payment);
            }
        }
        
        // Actualizar campos adicionales si es necesario
        if (invoiceDto.getAdditionalFields() != null && !invoiceDto.getAdditionalFields().isEmpty()) {
            // Eliminar campos adicionales actuales
            existingInvoice.getAdditionalFields().clear();
            
            // Agregar nuevos campos adicionales
            for (InvoiceAdditionalFieldDto fieldDto : invoiceDto.getAdditionalFields()) {
                InvoiceAdditionalField field = mapInvoiceAdditionalFieldDtoToEntity(fieldDto);
                existingInvoice.addAdditionalField(field);
            }
        }
        
        // Actualizar impuestos si es necesario
        if (invoiceDto.getTaxes() != null && !invoiceDto.getTaxes().isEmpty()) {
            // Eliminar impuestos actuales
            existingInvoice.getTaxes().clear();
            
            // Agregar nuevos impuestos
            for (InvoiceTaxDto taxDto : invoiceDto.getTaxes()) {
                InvoiceTax tax = mapInvoiceTaxDtoToEntity(taxDto);
                existingInvoice.addTax(tax);
            }
        }
        
        // Actualizar timestamp
        existingInvoice.setUpdatedAt(LocalDateTime.now());
        
        // Guardar cambios
        invoiceWriteRepository.save(existingInvoice);
        log.info("Factura actualizada correctamente, ID: {}", existingInvoice.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public InvoiceDto findById(UUID id) {
        log.info("Buscando factura con ID: {}", id);
        
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new BusinessInvoiceException(DomainErrorInvoiceMessage.INVOICE_NOT_FOUND, 
                        "Factura no encontrada con ID: " + id));
        
        return mapEntityToDto(invoice);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InvoiceDto> search(UUID customerId, String documentNumber, InvoiceStatus status) {
        log.info("Buscando facturas con filtros - customerId: {}, documentNumber: {}, status: {}", 
                customerId, documentNumber, status);
        
        // Usar el método searchByCriteria del repositorio para una búsqueda más eficiente
        List<Invoice> invoices = invoiceRepository.searchByCriteria(customerId, documentNumber, status);
        
        return invoices.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void changeStatus(UUID id, InvoiceStatus status) {
        log.info("Cambiando estado de la factura ID: {} a {}", id, status);
        
        // Verificar que la factura exista
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new BusinessInvoiceException(DomainErrorInvoiceMessage.INVOICE_NOT_FOUND, 
                        "Factura no encontrada con ID: " + id));
        
        // Validar transiciones de estado permitidas
        validateStatusTransition(invoice.getStatus(), status);
        
        // Actualizar el estado
        invoice.setStatus(status);
        invoice.setUpdatedAt(LocalDateTime.now());
        
        // Si el estado es AUTHORIZED, actualizar la fecha de autorización
        if (status == InvoiceStatus.AUTHORIZED && invoice.getAuthorizationDate() == null) {
            invoice.setAuthorizationDate(LocalDateTime.now());
        }
        
        // Guardar los cambios
        invoiceWriteRepository.save(invoice);
        log.info("Estado de factura actualizado correctamente, ID: {}, nuevo estado: {}", invoice.getId(), status);
    }
    
    // Métodos auxiliares
    
    private void validateRequiredFields(InvoiceDto invoiceDto) {
        if (invoiceDto.getSubtotal() == null || invoiceDto.getTotalAmount() == null) {
            throw new BusinessInvoiceException(DomainErrorInvoiceMessage.INVALID_INVOICE_DATA, 
                    "Los campos subtotal y monto total son obligatorios");
        }
        
        if (invoiceDto.getIssuer() == null || invoiceDto.getIssuer().getId() == null) {
            throw new BusinessInvoiceException(DomainErrorInvoiceMessage.INVALID_INVOICE_DATA, 
                    "El emisor de la factura es obligatorio");
        }
        
        if (invoiceDto.getCustomer() == null || invoiceDto.getCustomer().getId() == null) {
            throw new BusinessInvoiceException(DomainErrorInvoiceMessage.INVALID_INVOICE_DATA, 
                    "El cliente de la factura es obligatorio");
        }
    }
    
    private String generateNextSequential() {
        // Obtener el último secuencial y generar el siguiente
        String lastSequential = invoiceRepository.findLastSequential().orElse("000000000");
        
        try {
            // Convertir a entero e incrementar
            int seq = Integer.parseInt(lastSequential);
            seq++;
            
            // Formatear con ceros a la izquierda (9 dígitos)
            return String.format("%09d", seq);
        } catch (NumberFormatException e) {
            log.error("Error al generar secuencial: {}", e.getMessage());
            return "000000001"; // Valor por defecto si hay error
        }
    }
    
    private InvoiceIssuer getInvoiceIssuer(InvoiceDto invoiceDto) {
        return invoiceIssuerRepository.findById(invoiceDto.getIssuer().getId())
                .orElseThrow(() -> new BusinessInvoiceException(DomainErrorInvoiceMessage.ISSUER_NOT_FOUND, 
                        "Emisor no encontrado con ID: " + invoiceDto.getIssuer().getId()));
    }
    
    private Customer getCustomer(InvoiceDto invoiceDto) {
        return customerRepository.findById(invoiceDto.getCustomer().getId())
                .orElseThrow(() -> new BusinessInvoiceException(DomainErrorInvoiceMessage.CUSTOMER_NOT_FOUND, 
                        "Cliente no encontrado con ID: " + invoiceDto.getCustomer().getId()));
    }
    
    private void validateStatusTransition(InvoiceStatus currentStatus, InvoiceStatus newStatus) {
        // Validar transiciones de estado permitidas
        if (currentStatus == InvoiceStatus.ANNULLED) {
            throw new BusinessInvoiceException(DomainErrorInvoiceMessage.INVALID_INVOICE_STATUS, 
                    "No se puede cambiar el estado de una factura anulada");
        }
        
        if (currentStatus == InvoiceStatus.AUTHORIZED && newStatus != InvoiceStatus.ANNULLED) {
            throw new BusinessInvoiceException(DomainErrorInvoiceMessage.INVALID_INVOICE_STATUS, 
                    "Una factura autorizada solo puede ser anulada");
        }
        
        // Aquí se pueden agregar más reglas de validación según la lógica de negocio
    }
    
    // Métodos de mapeo entre entidades y DTOs
    
    private InvoiceDetail mapInvoiceDetailDtoToEntity(InvoiceDetailDto dto) {
        return InvoiceDetail.builder()
                .id(dto.getId() != null ? dto.getId() : UUID.randomUUID())
                .lineNumber(dto.getLineNumber())
                .mainCode(dto.getCode()) // Usar el campo code del DTO como mainCode en la entidad
                .description(dto.getDescription())
                .quantity(dto.getQuantity())
                .unitPrice(dto.getUnitPrice())
                .discount(dto.getDiscount())
                .subtotal(dto.getSubtotal())
                // Establecer valores predeterminados para otros campos requeridos
                .totalWithTax(dto.getSubtotal()) // Valor temporal, debería calcularse correctamente
                .build();
    }
    
    private InvoicePayment mapInvoicePaymentDtoToEntity(InvoicePaymentDto dto) {
        return InvoicePayment.builder()
                .id(dto.getId() != null ? dto.getId() : UUID.randomUUID())
                .paymentMethod(dto.getPaymentType()) // Mapeo de paymentType a paymentMethod
                .paymentMethodName(dto.getReference()) // Usar reference como nombre del método de pago
                .amount(dto.getAmount())
                // Establecer valores predeterminados para campos adicionales
                .timeUnit(null) // Estos valores pueden ser actualizados según la lógica de negocio
                .timeValue(null)
                .build();
    }
    
    private InvoiceAdditionalField mapInvoiceAdditionalFieldDtoToEntity(InvoiceAdditionalFieldDto dto) {
        return InvoiceAdditionalField.builder()
                .id(dto.getId() != null ? dto.getId() : UUID.randomUUID())
                .name(dto.getName())
                .value(dto.getValue())
                .build();
    }
    
    private InvoiceTax mapInvoiceTaxDtoToEntity(InvoiceTaxDto dto) {
        return InvoiceTax.builder()
                .id(dto.getId() != null ? dto.getId() : UUID.randomUUID())
                .taxCode(dto.getCode()) // Mapeo de code a taxCode
                .rateCode(dto.getDescription()) // Usar description como rateCode (podría requerir validación adicional)
                .ratePercentage(dto.getRate()) // Mapeo de rate a ratePercentage
                .taxableAmount(dto.getBaseAmount()) // Mapeo de baseAmount a taxableAmount
                .taxAmount(dto.getTaxAmount())
                .build();
    }
    
    private InvoiceDto mapEntityToDto(Invoice invoice) {
        InvoiceDto dto = InvoiceDto.builder()
                .id(invoice.getId())
                .documentNumber(invoice.getDocumentNumber())
                .sequential(invoice.getSequential())
                .accessKey(invoice.getAccessKey())
                .emissionDate(invoice.getEmissionDate())
                .authorizationDate(invoice.getAuthorizationDate())
                .authorizationNumber(invoice.getAuthorizationNumber())
                .subtotal(invoice.getSubtotal())
                .discount(invoice.getDiscount())
                .taxAmount(invoice.getTaxAmount())
                .totalAmount(invoice.getTotalAmount())
                .tip(invoice.getTip())
                .status(invoice.getStatus())
                .remissionGuide(invoice.getRemissionGuide())
                .createdAt(invoice.getCreatedAt())
                .updatedAt(invoice.getUpdatedAt())
                .build();
        
        // Mapear emisor
        if (invoice.getIssuer() != null) {
            InvoiceIssuerDTO issuerDto = InvoiceIssuerDTO.builder()
                    .id(invoice.getIssuer().getId())
                    .businessName(invoice.getIssuer().getBusinessName())
                    .ruc(invoice.getIssuer().getRuc())
                    // Agregar más campos según sea necesario
                    .build();
            dto.setIssuer(issuerDto);
        }
        
        // Mapear cliente
        if (invoice.getCustomer() != null) {
            CustomerDto customerDto = CustomerDto.builder()
                    .id(invoice.getCustomer().getId())
                    .identificationNumber(invoice.getCustomer().getIdNumber())
                    .identificationType(invoice.getCustomer().getIdType())
                    .businessName(invoice.getCustomer().getBusinessName())
                    .address(invoice.getCustomer().getAddress())
                    .email(invoice.getCustomer().getEmail())
                    .phoneNumber(invoice.getCustomer().getPhone())
                    .isActive(invoice.getCustomer().getIsActive())
                    .build();
            dto.setCustomer(customerDto);
        }
        
        // Mapear detalles
        List<InvoiceDetailDto> detailDtos = new ArrayList<>();
        if (invoice.getDetails() != null) {
            for (InvoiceDetail detail : invoice.getDetails()) {
                InvoiceDetailDto detailDto = InvoiceDetailDto.builder()
                        .id(detail.getId())
                        .lineNumber(detail.getLineNumber())
                        .code(detail.getMainCode()) // Usar mainCode como code en el DTO
                        .description(detail.getDescription())
                        .quantity(detail.getQuantity())
                        .unitPrice(detail.getUnitPrice())
                        .discount(detail.getDiscount())
                        .subtotal(detail.getSubtotal())
                        .build();
                detailDtos.add(detailDto);
            }
        }
        dto.setDetails(detailDtos);
        
        // Mapear pagos
        List<InvoicePaymentDto> paymentDtos = new ArrayList<>();
        if (invoice.getPayments() != null) {
            for (InvoicePayment payment : invoice.getPayments()) {
                InvoicePaymentDto paymentDto = InvoicePaymentDto.builder()
                        .id(payment.getId())
                        .paymentType(payment.getPaymentMethod()) // Mapeo de paymentMethod a paymentType
                        .amount(payment.getAmount())
                        .reference(payment.getPaymentMethodName()) // Mapeo de paymentMethodName a reference
                        .build();
                paymentDtos.add(paymentDto);
            }
        }
        dto.setPayments(paymentDtos);
        
        // Mapear campos adicionales
        List<InvoiceAdditionalFieldDto> fieldDtos = new ArrayList<>();
        if (invoice.getAdditionalFields() != null) {
            for (InvoiceAdditionalField field : invoice.getAdditionalFields()) {
                InvoiceAdditionalFieldDto fieldDto = InvoiceAdditionalFieldDto.builder()
                        .id(field.getId())
                        .name(field.getName())
                        .value(field.getValue())
                        .build();
                fieldDtos.add(fieldDto);
            }
        }
        dto.setAdditionalFields(fieldDtos);
        
        // Mapear impuestos
        List<InvoiceTaxDto> taxDtos = new ArrayList<>();
        if (invoice.getTaxes() != null) {
            for (InvoiceTax tax : invoice.getTaxes()) {
                InvoiceTaxDto taxDto = InvoiceTaxDto.builder()
                        .id(tax.getId())
                        .code(tax.getTaxCode()) // Mapeo de taxCode a code
                        .description(tax.getRateCode()) // Mapeo de rateCode a description
                        .rate(tax.getRatePercentage()) // Mapeo de ratePercentage a rate
                        .baseAmount(tax.getTaxableAmount()) // Mapeo de taxableAmount a baseAmount
                        .taxAmount(tax.getTaxAmount())
                        .build();
                taxDtos.add(taxDto);
            }
        }
        dto.setTaxes(taxDtos);
        
        return dto;
    }
}
