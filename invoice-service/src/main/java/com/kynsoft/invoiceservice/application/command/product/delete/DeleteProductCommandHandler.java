package com.kynsoft.invoiceservice.application.command.product.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.invoiceservice.domain.service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteProductCommandHandler implements ICommandHandler<DeleteProductCommand> {

    private final IProductService productService;

    @Override
    @Transactional
    public void handle(DeleteProductCommand command) {
        log.info("Eliminando producto con ID: {}", command.getId());
        
        // Utilizar el servicio para eliminar el producto
        productService.delete(command.getId());
    }
}
