package com.kynsoft.invoiceservice.application.command.tax.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.invoiceservice.domain.service.ITaxService;
import com.kynsoft.invoiceservice.infrastructure.entities.Tax;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Manejador para el comando de creación de impuestos
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CreateTaxCommandHandler implements ICommandHandler<CreateTaxCommand > {

    private final ITaxService taxService;

    @Override
    @Transactional
    public void handle(CreateTaxCommand command) {
        log.info("Procesando comando de creación para impuesto: {}", command.getName());
        
        // Verificar si ya existe un impuesto con el mismo código
        taxService.findByCode(command.getCode()).ifPresent(existingTax -> {
            throw new RuntimeException("Ya existe un impuesto con el código: " + command.getCode());
        });
        
        // Crear la entidad de impuesto
        Tax tax = Tax.builder()
                .id(command.getId())
                .code(command.getCode())
                .name(command.getName())
                .description(command.getDescription())
                .value(command.getValue())
                .taxType(command.getTaxType())
                .status(true)
                .isPredetermined(command.getIsPredetermined())
                .createdBy(command.getCreatedBy())
                .updatedBy(command.getCreatedBy())
                .build();
        
        // Guardar el impuesto
        Tax savedTax = taxService.create(tax);
        command.setId(savedTax.getId());
        
        log.info("Impuesto creado exitosamente con ID: {}", savedTax.getId());

    }
}
