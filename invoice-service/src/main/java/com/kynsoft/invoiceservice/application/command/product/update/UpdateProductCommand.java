package com.kynsoft.invoiceservice.application.command.product.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class UpdateProductCommand implements ICommand {
    private UUID id;
    private UUID productId;
    private String name;
    private String description;
    private String mainCode;
    private String auxiliaryCode;
    private BigDecimal price;
    private String taxCode;
    private BigDecimal taxPercentage;
    private String iceCode;
    private BigDecimal icePercentage;
    private String productType;
    private Boolean status;
    private UUID categoryId;
    private UUID updatedBy;

    public UpdateProductCommand() {
        this.id = UUID.randomUUID();
    }

    public static UpdateProductCommand fromRequest(UpdateProductRequest request, UUID productId, UUID updatedBy) {
        UpdateProductCommand command = new UpdateProductCommand();
        command.setProductId(productId);
        command.setName(request.getName());
        command.setDescription(request.getDescription());
        command.setMainCode(request.getMainCode());
        command.setAuxiliaryCode(request.getAuxiliaryCode());
        command.setPrice(request.getPrice());
        command.setTaxCode(request.getTaxCode());
        command.setTaxPercentage(request.getTaxPercentage());
        command.setIceCode(request.getIceCode());
        command.setIcePercentage(request.getIcePercentage());
        command.setProductType(request.getProductType());
        command.setStatus(request.getStatus());
        command.setCategoryId(request.getCategoryId());
        command.setUpdatedBy(updatedBy);
        return command;
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateProductMessage(id);
    }
}