package com.kynsoft.invoiceservice.application.command.productcategory.delete;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteProductCategoryMessage {
    private UUID id;
    private String message;
}
