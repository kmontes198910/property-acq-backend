package com.kynsoft.invoiceservice.application.command.invoiceIssuer.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateInvoiceIssuerCommand implements ICommand {
    private UUID id;
    private String ruc;
    private String businessName;
    private String commercialName;
    private String establishment;
    private boolean pointOfSale;
    private String address;
    private String emissionPoint;
    private String email;
    private String phone;
    private String specialTaxpayer;
    private String retentionAgent;
    private String rimpeRegime;
    private String logoUrl;
    private String environment;
    private Boolean status;
    private String digitalCertP12;
    private String digitalCertPassword;
    private UUID createdBy;

    public CreateInvoiceIssuerCommand() {
        this.id = UUID.randomUUID();
    }

    public static CreateInvoiceIssuerCommand fromRequest(CreateInvoiceIssuerRequest request, UUID createdBy) {
        CreateInvoiceIssuerCommand command = new CreateInvoiceIssuerCommand();
        command.setRuc(request.getRuc());
        command.setBusinessName(request.getBusinessName());
        command.setCommercialName(request.getCommercialName());
        command.setEstablishment(request.getEstablishment());
        command.setPointOfSale(request.getPointOfSale());
        command.setAddress(request.getAddress());
        command.setEmissionPoint(request.getEmissionPoint());
        command.setEmail(request.getEmail());
        command.setPhone(request.getPhone());
        command.setSpecialTaxpayer(request.getSpecialTaxpayer());
        command.setRetentionAgent(request.getRetentionAgent());
        command.setRimpeRegime(request.getRimpeRegime());
        command.setLogoUrl(request.getLogoUrl());
        command.setEnvironment(request.getEnvironment());
        command.setStatus(request.getStatus());
        command.setDigitalCertP12(request.getDigitalCertP12());
        command.setDigitalCertPassword(request.getDigitalCertPassword());
        command.setCreatedBy(createdBy);
        return command;
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateInvoiceIssuerMessage(id);
    }
}