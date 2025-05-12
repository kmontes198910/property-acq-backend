package com.kynsoft.invoiceservice.application.command.invoiceIssuer.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateInvoiceIssuerCommand implements ICommand {
    private UUID id;
    private UUID issuerId;
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
    private UUID updatedBy;

    public UpdateInvoiceIssuerCommand() {
        this.id = UUID.randomUUID();
    }

    public static UpdateInvoiceIssuerCommand fromRequest(UpdateInvoiceIssuerRequest request, UUID issuerId, UUID updatedBy) {
        UpdateInvoiceIssuerCommand command = new UpdateInvoiceIssuerCommand();
        command.setIssuerId(issuerId);
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
        command.setUpdatedBy(updatedBy);
        return command;
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateInvoiceIssuerMessage(id);
    }
}