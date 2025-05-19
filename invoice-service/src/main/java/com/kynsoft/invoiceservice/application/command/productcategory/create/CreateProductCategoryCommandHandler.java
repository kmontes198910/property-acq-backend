package com.kynsoft.invoiceservice.application.command.productcategory.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.invoiceservice.application.query.productcategory.get.ProductCategoryDto;
import com.kynsoft.invoiceservice.domain.service.IProductCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Manejador para el comando de creación de categoría de producto
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CreateProductCategoryCommandHandler implements ICommandHandler<CreateProductCategoryCommand> {

    private final IProductCategoryService productCategoryService;

    @Override
    @Transactional
    public void handle(CreateProductCategoryCommand command) {
        log.info("Creando nueva categoría de producto: {}", command.getName());
        
        // Mapear el comando a un DTO
        ProductCategoryDto categoryDto = ProductCategoryDto.builder()
                .id(command.getId())
                .name(command.getName())
                .description(command.getDescription())
                .status(command.getStatus())
                .createdBy(command.getCreatedBy())
                .updatedBy(command.getCreatedBy()) // Al crear, ambos campos son iguales
                .build();
        
        // Utilizar el servicio para crear la categoría
       UUID id = productCategoryService.create(categoryDto);
       command.setId(id);

        log.info("Categoría de producto creada con ID: {}", id);
    }
}
