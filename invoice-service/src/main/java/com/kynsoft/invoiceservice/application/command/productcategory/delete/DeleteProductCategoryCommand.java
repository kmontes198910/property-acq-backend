package com.kynsoft.invoiceservice.application.command.productcategory.delete;

import com.kynsof.share.core.domain.bus.command.ICommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteProductCategoryCommand implements ICommand {
    private UUID id;
    private UUID userId;

    public static DeleteProductCategoryCommand fromRequest(UUID id, UUID userId) {
        return DeleteProductCategoryCommand.builder()
                .id(id)
                .userId(userId)
                .build();
    }
}
