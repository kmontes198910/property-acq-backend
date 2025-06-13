package com.kynsoft.invoiceservice.application.command.Issuer.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.invoiceservice.domain.service.IInvoiceIssuerService;
import com.kynsoft.invoiceservice.infrastructure.entities.Issuer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateInvoiceIssuerCommandHandler implements ICommandHandler<CreateInvoiceIssuerCommand> {

    private final IInvoiceIssuerService iInvoiceIssuerService;

    @Override
    @Transactional
    public void handle(CreateInvoiceIssuerCommand command) {
        log.info("Creating new invoice issuer: {}", command.getBusinessName());
        
        Issuer issuer = Issuer.builder()
                .id(UUID.randomUUID())
                .ruc(command.getRuc())
                .businessName(command.getBusinessName())
                .commercialName(command.getCommercialName())
                .establishment(command.getEstablishment())
                .pointOfSale(command.isPointOfSale())
                .address(command.getAddress())
                .emissionPoint(command.getEmissionPoint())
                .email(command.getEmail())
                .website(command.getWebsite())
                .phone(command.getPhone())
                .currency("USD") // Default currency
                .colorFactura(command.getColorFactura())
                .specialTaxpayer(command.getSpecialTaxpayer())
                .retentionAgent(command.getRetentionAgent())
                .accountingObligated(command.getAccountingObligated())
                .microenterprisesRegime(command.getMicroenterprisesRegime())
                .rimpeRegime(command.getRimpeRegime())
                .logoUrl(command.getLogoUrl())
                .sendEmails(command.getSendEmails())
                .status(command.getStatus())
                .digitalCertP12(command.getDigitalCertP12())
                .digitalCertPassword(command.getDigitalCertPassword())
                .build();
        
        Issuer savedIssuer = iInvoiceIssuerService.create(issuer);
        
        // Asignamos el ID generado al comando para que esté disponible en el mensaje de respuesta
        command.setId(savedIssuer.getId());
    }
}