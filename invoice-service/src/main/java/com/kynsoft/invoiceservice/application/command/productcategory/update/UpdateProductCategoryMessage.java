package com.kynsoft.invoiceservice.application.command.productcategory.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Mensaje de respuesta para la actualización de una categoría de producto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductCategoryMessage implements ICommandMessage {
    private UUID id;
}
