package com.kynsoft.finamer.digitalsignature.application.command.digitalsignaturecertificate.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.exception.BusinessException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsoft.finamer.digitalsignature.domain.service.IDigitalSignatureCertificateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteDigitalSignatureCertificateCommandHandler implements ICommandHandler<DeleteDigitalSignatureCertificateCommand> {

    private final IDigitalSignatureCertificateService digitalSignatureCertificateService;

    @Override
    @Transactional
    public void handle(DeleteDigitalSignatureCertificateCommand command) {
        log.info("Eliminando firma digital con ID: {}", command.getId());
        
        boolean deleted = digitalSignatureCertificateService.delete(command.getId(), command.getDeletedBy());
        
        if (!deleted) {
            throw new BusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND, 
                "Firma digital no encontrada con ID: " + command.getId());
        }
        
        log.info("Firma digital eliminada con ID: {}", command.getId());
    }
}