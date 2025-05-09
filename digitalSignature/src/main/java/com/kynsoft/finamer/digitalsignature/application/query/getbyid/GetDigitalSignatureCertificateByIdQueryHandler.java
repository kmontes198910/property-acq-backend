package com.kynsoft.finamer.digitalsignature.application.query.getbyid;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.finamer.digitalsignature.domain.dto.DigitalSignatureCertificateDto;
import com.kynsoft.finamer.digitalsignature.domain.exception.BusinessException;
import com.kynsoft.finamer.digitalsignature.domain.exception.DigitalSignatureErrorMessage;
import com.kynsoft.finamer.digitalsignature.domain.service.IDigitalSignatureCertificateService;
import com.kynsoft.finamer.digitalsignature.infrastructure.entity.DigitalSignatureCertificate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetDigitalSignatureCertificateByIdQueryHandler implements IQueryHandler<GetDigitalSignatureCertificateByIdQuery, DigitalSignatureCertificateResponse> {

    private final IDigitalSignatureCertificateService digitalSignatureCertificateService;

    @Override
    public DigitalSignatureCertificateResponse handle(GetDigitalSignatureCertificateByIdQuery query) {
        log.info("Obteniendo firma digital con ID: {}", query.getId());
        
        Optional<DigitalSignatureCertificate> certificateOpt = digitalSignatureCertificateService.findById(query.getId());
        
        if (certificateOpt.isEmpty()) {
            throw new BusinessException(DigitalSignatureErrorMessage.CERTIFICATE_NOT_FOUND, query.getId().toString());
        }
        
        DigitalSignatureCertificate certificate = certificateOpt.get();
        DigitalSignatureCertificateDto certificateDto = mapToDto(certificate);
        
        return new DigitalSignatureCertificateResponse(certificateDto);
    }
    
    private DigitalSignatureCertificateDto mapToDto(DigitalSignatureCertificate entity) {
        return DigitalSignatureCertificateDto.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .certificateName(entity.getCertificateName())
                .certificateP12Base64(entity.getCertificateP12() != null ? 
                    java.util.Base64.getEncoder().encodeToString(entity.getCertificateP12()) : null)
                .certificatePassword(entity.getCertificatePassword())
                .expirationDate(entity.getExpirationDate())
                .isActive(entity.getIsActive())
                .businessId(entity.getBusiness() != null ? entity.getBusiness().getId() : null)
                .businessName(entity.getBusiness() != null ? entity.getBusiness().getName() : null)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}