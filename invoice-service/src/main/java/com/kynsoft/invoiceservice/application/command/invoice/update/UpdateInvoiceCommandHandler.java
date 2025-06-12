package com.kynsoft.invoiceservice.application.command.invoice.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.invoiceservice.domain.dto.InvoiceDto;
import com.kynsoft.invoiceservice.domain.exception.BusinessInvoiceException;
import com.kynsoft.invoiceservice.domain.exception.DomainErrorInvoiceMessage;
import com.kynsoft.invoiceservice.domain.service.IInvoiceService;
import com.kynsoft.invoiceservice.infrastructure.entities.InvoiceStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

        // Actualizar campos de auditoría
        invoice.setUpdatedAt(LocalDateTime.now());
        invoice.setUpdatedBy(command.getUpdatedBy());
    }
}
