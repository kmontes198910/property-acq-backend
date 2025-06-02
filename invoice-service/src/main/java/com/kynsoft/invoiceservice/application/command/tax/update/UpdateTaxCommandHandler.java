package com.kynsoft.invoiceservice.application.command.tax.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.invoiceservice.domain.service.ITaxService;
import com.kynsoft.invoiceservice.infrastructure.entities.Tax;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

/**
 * Manejador para el comando de actualización de impuestos
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UpdateTaxCommandHandler implements ICommandHandler<UpdateTaxCommand> {

    private final ITaxService taxService;

    @Override
    @Transactional
    public void handle(UpdateTaxCommand command) {
        log.info("Procesando comando de actualización para impuesto con ID: {}", command.getId());
        
        // Buscar el impuesto existente
        Tax existingTax = taxService.findById(command.getId())
                .orElseThrow(() -> new NoSuchElementException("Impuesto no encontrado con ID: " + command.getId()));
        
        // Actualizar los campos del impuesto
        existingTax.setName(command.getName());
        existingTax.setDescription(command.getDescription());
        existingTax.setValue(command.getValue());
        existingTax.setTaxType(command.getTaxType());
        existingTax.setIsPredetermined(command.getIsPredetermined());
        existingTax.setStatus(command.getStatus());
        existingTax.setUpdatedBy(command.getUpdatedBy());
        
        // Guardar los cambios
        taxService.update(existingTax);
        
        log.info("Impuesto actualizado exitosamente con ID: {}", existingTax.getId());
    }
}
