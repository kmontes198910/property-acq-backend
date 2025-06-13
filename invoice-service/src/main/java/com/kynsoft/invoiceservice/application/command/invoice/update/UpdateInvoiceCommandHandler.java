package com.kynsoft.invoiceservice.application.command.invoice.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.invoiceservice.application.command.invoice.update.request.CampoAdicionalRequest;
import com.kynsoft.invoiceservice.application.command.invoice.update.request.CustomerRequest;
import com.kynsoft.invoiceservice.application.command.invoice.update.request.DetalleFacturaRequest;
import com.kynsoft.invoiceservice.application.command.invoice.update.request.PagoRequest;
import com.kynsoft.invoiceservice.domain.dto.*;
import com.kynsoft.invoiceservice.domain.exception.BusinessInvoiceException;
import com.kynsoft.invoiceservice.domain.exception.DomainErrorInvoiceMessage;
import com.kynsoft.invoiceservice.domain.service.IInvoiceService;
import com.kynsoft.invoiceservice.infrastructure.entities.InvoiceStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Controlador para manejar la actualización de facturas
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateInvoiceCommandHandler implements ICommandHandler<UpdateInvoiceCommand> {

    private final IInvoiceService invoiceService;

    @Override
    public void handle(UpdateInvoiceCommand command) {
        log.info("Actualizando factura con ID: {}", command.getId());
        
        try {
            // Obtener la factura existente
            InvoiceDto existingInvoice = invoiceService.findById(command.getId());
            
            // Validar que la factura no esté en un estado que no permita actualizaciones
            if (existingInvoice.getStatus() == InvoiceStatus.AUTHORIZED || 
                existingInvoice.getStatus() == InvoiceStatus.ANNULLED) {
                throw new BusinessInvoiceException(DomainErrorInvoiceMessage.INVALID_INVOICE_STATUS, 
                        "No se puede actualizar una factura con estado " + existingInvoice.getStatus());
            }
            
            // Actualizar campos de la factura
            updateInvoiceFields(existingInvoice, command);
            
            // Guardar cambios
            invoiceService.update(existingInvoice);

        } catch (BusinessInvoiceException e) {
            log.error("Error de negocio al actualizar factura: {}", e.getMessage());

        } catch (Exception e) {
            log.error("Error al actualizar factura: {}", e.getMessage(), e);
        }
    }
    
    /**
     * Actualiza los campos de la factura con los datos del comando
     * 
     * @param invoice La factura a actualizar
     * @param command El comando con los datos a aplicar
     */
    private void updateInvoiceFields(InvoiceDto invoice, UpdateInvoiceCommand command) {
        // Actualizar estado de la factura si se proporciona
        if (command.getStatus() != null) {
            invoice.setStatus(command.getStatus());
        }
        
        // Actualizar información del emisor si se proporciona
        if (command.getIssuerId() != null) {
            // Actualizar el ID del emisor
            if (invoice.getIssuer() == null) {
                invoice.setIssuer(new DigitalCertificateDTO.IssuerDTO());
            }
            invoice.getIssuer().setId(command.getIssuerId());
        }
        
        // Actualizar información del cliente si se proporciona
        if (command.getCustomer() != null) {
            CustomerRequest customerReq = command.getCustomer();
            
            if (invoice.getCustomer() == null) {
                invoice.setCustomer(new com.kynsoft.invoiceservice.application.query.customer.get.CustomerDto());
            }
            
            // Actualizar solo los campos proporcionados del cliente
            if (customerReq.getIdentificationType() != null) {
                invoice.getCustomer().setIdentificationType(customerReq.getIdentificationType());
            }
            if (customerReq.getBusinessName() != null) {
                invoice.getCustomer().setBusinessName(customerReq.getBusinessName());
            }
            if (customerReq.getIdentificationNumber() != null) {
                invoice.getCustomer().setIdentificationNumber(customerReq.getIdentificationNumber());
            }
            if (customerReq.getAddress() != null) {
                invoice.getCustomer().setAddress(customerReq.getAddress());
            }
            if (customerReq.getEmail() != null) {
                invoice.getCustomer().setEmail(customerReq.getEmail());
            }
            if (customerReq.getPhoneNumber() != null) {
                invoice.getCustomer().setPhoneNumber(customerReq.getPhoneNumber());
            }
        }
        
        // Actualizar la propina si se proporciona
        if (command.getPropina() != null) {
            invoice.setTip(command.getPropina());
        }
        
        // Actualizar detalles de la factura si se proporcionan
        if (command.getDetalles() != null && !command.getDetalles().isEmpty()) {
            List<com.kynsoft.invoiceservice.domain.dto.InvoiceDetailDto> detalles = new ArrayList<>();
            
            for (DetalleFacturaRequest detalle : command.getDetalles()) {
                InvoiceDetailDto detalleDto = InvoiceDetailDto.builder()
                    .id(UUID.randomUUID())  // Generar nuevo ID o mantener existente
                    .mainCode(detalle.getCodigoPrincipal())
                    .auxiliaryCode(detalle.getCodigoAuxiliar())
                    .description(detalle.getDescripcion())
                    .quantity(detalle.getCantidad())
                    .unitPrice(detalle.getPrecioUnitario())
                    .unitOfMeasure(detalle.getUnidadMedida())
                    .discount(detalle.getDescuento())
                    .subtotal(calcularSubtotal(detalle))
                    .build();
                
                // Agregar impuestos si están disponibles
                if (detalle.getTipoImpuesto() != null && detalle.getPorcentajeImpuesto() != null) {
                    InvoiceDetailTaxDto taxDto = InvoiceDetailTaxDto.builder()
                        .id(UUID.randomUUID())
                        .code(detalle.getTipoImpuesto())
                        .rate(detalle.getPorcentajeImpuesto())
                        .build();
                    
                    // Calcular valor del impuesto
                    BigDecimal taxableBase = detalleDto.getSubtotal();
                    BigDecimal taxValue = taxableBase.multiply(detalle.getPorcentajeImpuesto()).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
                    taxDto.setTaxableBase(taxableBase);
                    taxDto.setValue(taxValue);
                    
                    // Agregar el impuesto al detalle
                    if (detalleDto.getTaxes() == null) {
                        detalleDto.setTaxes(new ArrayList<>());
                    }
                    detalleDto.getTaxes().add(taxDto);
                    
                    // Actualizar total con impuestos
                    detalleDto.setTotalWithTax(taxableBase.add(taxValue));
                }
                
                detalles.add(detalleDto);
            }
            
            invoice.setDetails(detalles);
        }
        
        // Actualizar pagos de la factura si se proporcionan
        if (command.getPagos() != null && !command.getPagos().isEmpty()) {
            List<InvoicePaymentDto> pagos = new ArrayList<>();
            
            for (PagoRequest pago : command.getPagos()) {
                InvoicePaymentDto pagoDto = InvoicePaymentDto.builder()
                    .id(UUID.randomUUID())  // Generar nuevo ID o mantener existente
                    .paymentType(pago.getFormaPago())
                    .reference(pago.getDescripcion())
                    .plazo(pago.getPlazo())
                    .unidadTiempo(pago.getUnidadTiempo())
                    .build();
                
                pagos.add(pagoDto);
            }
            
            invoice.setPayments(pagos);
        }
        
        // Actualizar información adicional de la factura si se proporciona
        if (command.getInfoAdicional() != null && !command.getInfoAdicional().isEmpty()) {
            List<InvoiceAdditionalFieldDto> campos = new ArrayList<>();
            
            for (CampoAdicionalRequest campo : command.getInfoAdicional()) {
                InvoiceAdditionalFieldDto campoDto = InvoiceAdditionalFieldDto.builder()
                    .id(UUID.randomUUID())  // Generar nuevo ID o mantener existente
                    .name(campo.getNombre())
                    .value(campo.getValor())
                    .build();
                
                campos.add(campoDto);
            }
            
            invoice.setAdditionalFields(campos);
        }
        
        // Actualizar campos de auditoría
        invoice.setUpdatedAt(LocalDateTime.now());
        invoice.setUpdatedBy(command.getUpdatedBy());
    }
    
    /**
     * Calcula el subtotal de un detalle de factura
     * @param detalle El detalle de factura
     * @return El subtotal calculado
     */
    private BigDecimal calcularSubtotal(DetalleFacturaRequest detalle) {
        BigDecimal subtotal = detalle.getCantidad().multiply(detalle.getPrecioUnitario());
        
        if (detalle.getDescuento() != null) {
            subtotal = subtotal.subtract(detalle.getDescuento());
        }
        
        return subtotal;
    }
}
