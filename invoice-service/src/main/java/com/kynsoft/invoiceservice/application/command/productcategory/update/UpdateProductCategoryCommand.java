package com.kynsoft.invoiceservice.application.command.productcategory.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Comando para actualizar una categoría de producto existente
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductCategoryCommand implements ICommand {
    private UUID id;
    private String name;
    private String description;
    private Boolean status;
    private UUID updatedBy;
    
    public static UpdateProductCategoryCommand fromRequest(UpdateProductCategoryRequest request, UUID updatedBy) {
        UpdateProductCategoryCommand command = new UpdateProductCategoryCommand(
                request.getId(),
                request.getName(),
                request.getDescription(),
                request.getStatus(),
                updatedBy
        );
        command.setUpdatedBy(updatedBy);
        return command;
    }
    
    @Override
    public ICommandMessage getMessage() {
        return new UpdateProductCategoryMessage(id);
    }
}
