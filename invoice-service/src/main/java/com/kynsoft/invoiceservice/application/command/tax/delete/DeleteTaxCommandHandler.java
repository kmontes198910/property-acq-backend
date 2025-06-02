package com.kynsoft.invoiceservice.application.command.tax.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.invoiceservice.domain.service.ITaxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Manejador para el comando de eliminación de impuestos
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DeleteTaxCommandHandler implements ICommandHandler<DeleteTaxCommand> {

    private final ITaxService taxService;

    @Override
    public void handle(DeleteTaxCommand command) {
        log.info("Procesando comando de eliminación para impuesto con ID: {}", command.getId());
        
        taxService.delete(command.getId());
    }
}
