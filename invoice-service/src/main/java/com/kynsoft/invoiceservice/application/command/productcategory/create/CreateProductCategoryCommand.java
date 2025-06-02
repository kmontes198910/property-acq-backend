package com.kynsoft.invoiceservice.application.command.productcategory.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Comando para crear una nueva categoría de producto
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductCategoryCommand implements ICommand {
    private UUID id;
    private String name;
    private String description;
    private Boolean status;
    private UUID createdBy;
    
    public static CreateProductCategoryCommand fromRequest(CreateProductCategoryRequest request, UUID createdBy) {
        CreateProductCategoryCommand command = new CreateProductCategoryCommand(
                UUID.randomUUID(),
                request.getName(),
                request.getDescription(),
                request.getStatus() != null ? request.getStatus() : true,
                createdBy
        );
        return command;
    }
    
    @Override
    public ICommandMessage getMessage() {
        return new CreateProductCategoryMessage(id);
    }
}
