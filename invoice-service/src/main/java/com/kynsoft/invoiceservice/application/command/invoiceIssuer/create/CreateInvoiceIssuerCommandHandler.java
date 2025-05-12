package com.kynsoft.invoiceservice.application.command.invoiceIssuer.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.invoiceservice.infrastructure.entities.InvoiceIssuer;
import com.kynsoft.invoiceservice.infrastructure.repository.command.InvoiceIssuerWriteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateInvoiceIssuerCommandHandler implements ICommandHandler<CreateInvoiceIssuerCommand> {

    private final InvoiceIssuerWriteRepository invoiceIssuerWriteRepository;

    @Override
    @Transactional
    public void handle(CreateInvoiceIssuerCommand command) {
        log.info("Creating new invoice issuer: {}", command.getBusinessName());
        
        InvoiceIssuer issuer = InvoiceIssuer.builder()
                .id(UUID.randomUUID())
                .ruc(command.getRuc())
                .businessName(command.getBusinessName())
                .commercialName(command.getCommercialName())
                .establishment(command.getEstablishment())
                .pointOfSale(command.isPointOfSale())
                .address(command.getAddress())
                .emissionPoint(command.getEmissionPoint())
                .email(command.getEmail())
                .phone(command.getPhone())
                .specialTaxpayer(command.getSpecialTaxpayer())
                .retentionAgent(command.getRetentionAgent())
                .rimpeRegime(command.getRimpeRegime())
                .logoUrl(command.getLogoUrl())
                .environment(command.getEnvironment())
                .status(command.getStatus())
                .digitalCertP12(command.getDigitalCertP12())
                .digitalCertPassword(command.getDigitalCertPassword())
                .build();
        
        InvoiceIssuer savedIssuer = invoiceIssuerWriteRepository.save(issuer);
        
        // Asignamos el ID generado al comando para que esté disponible en el mensaje de respuesta
        command.setId(savedIssuer.getId());
    }
}