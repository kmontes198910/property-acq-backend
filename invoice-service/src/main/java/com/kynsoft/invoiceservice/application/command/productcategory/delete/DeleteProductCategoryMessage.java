package com.kynsoft.invoiceservice.application.command.productcategory.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Mensaje de respuesta para la creación de una categoría de producto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteProductCategoryMessage implements ICommandMessage {
    private UUID id;
}
