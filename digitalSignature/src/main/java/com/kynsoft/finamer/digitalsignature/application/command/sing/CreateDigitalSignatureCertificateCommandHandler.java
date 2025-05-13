package com.kynsoft.finamer.digitalsignature.application.command.sing;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.finamer.digitalsignature.domain.service.IDigitalSignatureCertificateService;
import com.kynsoft.finamer.digitalsignature.infrastructure.entity.DigitalSignatureCertificate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateDigitalSignatureCertificateCommandHandler implements ICommandHandler<CreateDigitalSignatureCertificateCommand> {

    private final IDigitalSignatureCertificateService digitalSignatureCertificateService;

    @Override
    @Transactional
    public void handle(CreateDigitalSignatureCertificateCommand command) {
        log.info("Creando nueva firma digital para usuario: {}", command.getUserId());
        
        DigitalSignatureCertificate certificate = DigitalSignatureCertificate.builder()
                .userId(command.getUserId())
                .certificateName(command.getCertificateName())
                .certificateP12(command.getCertificateP12())
                .certificatePassword(command.getCertificatePassword())
                .expirationDate(command.getExpirationDate())
                .isActive(true)
                .isPrimaryKey(command.getIsPrimaryKey() != null ? command.getIsPrimaryKey() : false)
                .businessId(command.getBusinessId()) // Se asignará en el servicio
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy(command.getCreatedBy())
                .updatedBy(command.getCreatedBy())
                .build();
        
        DigitalSignatureCertificate createdCertificate = digitalSignatureCertificateService.create(certificate);
        command.setId(createdCertificate.getId());
        
        log.info("Firma digital creada con ID: {}", createdCertificate.getId());
    }
}