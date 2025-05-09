package com.kynsoft.finamer.digitalsignature.application.command.digitalsignaturecertificate.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.exception.BusinessException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsoft.finamer.digitalsignature.domain.service.IDigitalSignatureCertificateService;
import com.kynsoft.finamer.digitalsignature.infrastructure.entity.DigitalSignatureCertificate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateDigitalSignatureCertificateCommandHandler implements ICommandHandler<UpdateDigitalSignatureCertificateCommand> {

    private final IDigitalSignatureCertificateService digitalSignatureCertificateService;

    @Override
    @Transactional
    public void handle(UpdateDigitalSignatureCertificateCommand command) {
        log.info("Actualizando firma digital con ID: {}", command.getId());
        
        Optional<DigitalSignatureCertificate> existingCertificateOpt = digitalSignatureCertificateService.findById(command.getId());
        
        if (existingCertificateOpt.isEmpty()) {
            throw new BusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND, 
                "Firma digital no encontrada con ID: " + command.getId());
        }
        
        DigitalSignatureCertificate existingCertificate = existingCertificateOpt.get();
        
        // Actualizar solo los campos que no son nulos en el comando
        if (command.getCertificateName() != null) {
            existingCertificate.setCertificateName(command.getCertificateName());
        }
        
        if (command.getCertificateP12() != null) {
            existingCertificate.setCertificateP12(command.getCertificateP12());
        }
        
        if (command.getCertificatePassword() != null) {
            existingCertificate.setCertificatePassword(command.getCertificatePassword());
        }
        
        if (command.getExpirationDate() != null) {
            existingCertificate.setExpirationDate(command.getExpirationDate());
        }
        
        if (command.getIsActive() != null) {
            existingCertificate.setIsActive(command.getIsActive());
        }
        
        existingCertificate.setUpdatedAt(LocalDateTime.now());
        
        digitalSignatureCertificateService.update(existingCertificate, command.getBusinessId(), command.getUpdatedBy());
        
        log.info("Firma digital actualizada con ID: {}", existingCertificate.getId());
    }
}