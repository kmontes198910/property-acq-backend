package com.kynsoft.invoiceservice.application.command.product.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class CreateProductCommand implements ICommand {
    private UUID id;
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
    private UUID categoryId;
    private UUID createdBy;

    public CreateProductCommand() {
        this.id = UUID.randomUUID();
    }

    public static CreateProductCommand fromRequest(CreateProductRequest request, UUID createdBy) {
        CreateProductCommand command = new CreateProductCommand();
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
        command.setCategoryId(request.getCategoryId());
        command.setCreatedBy(createdBy);
        return command;
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateProductMessage(id);
    }
}