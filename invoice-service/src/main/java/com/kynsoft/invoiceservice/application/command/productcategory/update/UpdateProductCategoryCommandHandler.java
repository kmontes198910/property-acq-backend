package com.kynsoft.invoiceservice.application.command.productcategory.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.invoiceservice.application.query.productcategory.get.ProductCategoryDto;
import com.kynsoft.invoiceservice.domain.service.IProductCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Manejador para el comando de actualización de categoría de producto
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateProductCategoryCommandHandler implements ICommandHandler<UpdateProductCategoryCommand> {

    private final IProductCategoryService productCategoryService;

    @Override
    @Transactional
    public void handle(UpdateProductCategoryCommand command) {
        log.info("Actualizando categoría de producto con ID: {}", command.getId());
        
        // Mapear el comando a un DTO
        ProductCategoryDto categoryDto = ProductCategoryDto.builder()
                .id(command.getId())
                .name(command.getName())
                .description(command.getDescription())
                .status(command.getStatus())
                .build();
        
        // Utilizar el servicio para actualizar la categoría
        productCategoryService.update(categoryDto);
    }
}
