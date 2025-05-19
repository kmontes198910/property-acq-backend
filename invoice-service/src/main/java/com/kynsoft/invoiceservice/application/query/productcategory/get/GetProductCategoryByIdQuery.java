package com.kynsoft.invoiceservice.application.query.productcategory.get;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Query para obtener una categoría de producto por su ID
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProductCategoryByIdQuery implements IQuery {
    private UUID id;
}
