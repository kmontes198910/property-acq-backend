package com.kynsoft.invoiceservice.application.command.customer.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.invoiceservice.domain.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteCustomerCommandHandler implements ICommandHandler<DeleteCustomerCommand> {

    private final ICustomerService customerService;

    @Override
    @Transactional
    public void handle(DeleteCustomerCommand command) {
        log.info("Deleting customer with ID: {}", command.getId());
        
        // Utilizar el servicio para eliminar el cliente
        customerService.delete(command.getId());
    }
}