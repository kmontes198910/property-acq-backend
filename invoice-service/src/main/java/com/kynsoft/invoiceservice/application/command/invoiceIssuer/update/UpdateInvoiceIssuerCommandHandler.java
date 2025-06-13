package com.kynsoft.invoiceservice.application.command.invoiceIssuer.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsoft.invoiceservice.infrastructure.entities.Issuer;
import com.kynsoft.invoiceservice.infrastructure.repository.command.InvoiceIssuerWriteRepository;
import com.kynsoft.invoiceservice.infrastructure.repository.query.InvoiceIssuerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateInvoiceIssuerCommandHandler implements ICommandHandler<UpdateInvoiceIssuerCommand> {

    private final InvoiceIssuerRepository invoiceIssuerRepository;
    private final InvoiceIssuerWriteRepository invoiceIssuerWriteRepository;

    @Override
    @Transactional
    public void handle(UpdateInvoiceIssuerCommand command) {
        log.info("Updating invoice issuer with ID: {}", command.getIssuerId());
        
        Issuer issuer = invoiceIssuerRepository.findById(command.getIssuerId())
                .orElseThrow(() -> new BusinessNotFoundException(
                    new GlobalBusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND, 
                    new ErrorField("id", "Invoice issuer not found with ID: " + command.getIssuerId()))));
        
        // Actualizar los campos del emisor
        issuer.setRuc(command.getRuc());
        issuer.setBusinessName(command.getBusinessName());
        issuer.setCommercialName(command.getCommercialName());
        issuer.setEstablishment(command.getEstablishment());
        issuer.setPointOfSale(command.isPointOfSale());
        issuer.setAddress(command.getAddress());
        issuer.setEmissionPoint(command.getEmissionPoint());
        issuer.setEmail(command.getEmail());
        issuer.setPhone(command.getPhone());
        issuer.setWebsite(command.getWebsite());
        issuer.setColorFactura(command.getColorFactura());
        issuer.setSpecialTaxpayer(command.getSpecialTaxpayer());
        issuer.setRetentionAgent(command.getRetentionAgent());
        issuer.setAccountingObligated(command.getAccountingObligated());
        issuer.setMicroenterprisesRegime(command.getMicroenterprisesRegime());
        issuer.setRimpeRegime(command.getRimpeRegime());
        issuer.setLogoUrl(command.getLogoUrl());
        issuer.setSendEmails(command.getSendEmails());
        
        issuer.setStatus(command.getStatus());
        
        // Solo actualizar estos campos si no son nulos, ya que son sensibles
        if (command.getDigitalCertP12() != null && !command.getDigitalCertP12().isEmpty()) {
            issuer.setDigitalCertP12(command.getDigitalCertP12());
        }
        
        if (command.getDigitalCertPassword() != null && !command.getDigitalCertPassword().isEmpty()) {
            issuer.setDigitalCertPassword(command.getDigitalCertPassword());
        }
        
        invoiceIssuerWriteRepository.save(issuer);
    }
}