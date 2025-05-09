package com.kynsoft.finamer.digitalsignature.infrastructure.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.finamer.digitalsignature.application.response.DigitalSignatureCertificateResponse;
import com.kynsoft.finamer.digitalsignature.domain.dto.DigitalSignatureCertificateDto;
import com.kynsoft.finamer.digitalsignature.domain.exception.BusinessException;
import com.kynsoft.finamer.digitalsignature.domain.exception.DigitalSignatureErrorMessage;
import com.kynsoft.finamer.digitalsignature.domain.service.IDigitalSignatureCertificateService;
import com.kynsoft.finamer.digitalsignature.infrastructure.entity.DigitalSignatureCertificate;
import com.kynsoft.finamer.digitalsignature.infrastructure.repository.query.DigitalSignatureCertificateReadRepository;
import com.kynsoft.finamer.digitalsignature.infrastructure.repository.command.DigitalSignatureCertificateWriteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class DigitalSignatureCertificateServiceImpl implements IDigitalSignatureCertificateService {

    private final DigitalSignatureCertificateReadRepository certificateReadRepository;
    private final DigitalSignatureCertificateWriteRepository certificateWriteRepository;


    @Override
    @Transactional
    public DigitalSignatureCertificate create(DigitalSignatureCertificate certificate) {
        log.info("Creando certificado de firma digital para usuario: {}", certificate.getUserId());

        // Generar un nuevo ID si no tiene uno
        if (certificate.getId() == null) {
            certificate.setId(UUID.randomUUID());
        }

        return certificateWriteRepository.save(certificate);
    }

    @Override
    @Transactional
    public DigitalSignatureCertificate update(DigitalSignatureCertificate certificate, UUID businessId, String updatedBy) {
        log.info("Actualizando certificado de firma digital con ID: {}", certificate.getId());

        // Verificar que el certificado existe
        certificateReadRepository.findById(certificate.getId())
                .orElseThrow(() -> new BusinessException(DigitalSignatureErrorMessage.CERTIFICATE_NOT_FOUND, certificate.getId().toString()));

        // Establecer quien actualiza el certificado
        certificate.setUpdatedBy(updatedBy);

        return certificateWriteRepository.save(certificate);
    }

    @Override
    @Transactional
    public boolean delete(UUID id, String deletedBy) {
        log.info("Eliminando certificado de firma digital con ID: {}", id);

        Optional<DigitalSignatureCertificate> certificate = certificateReadRepository.findById(id);
        if (certificate.isEmpty()) {
            return false;
        }

        certificateWriteRepository.deleteById(id);
        return true;
    }

    @Override
    public Optional<DigitalSignatureCertificate> findById(UUID id) {
        log.info("Buscando certificado de firma digital con ID: {}", id);
        return certificateReadRepository.findById(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filters, String query) {

        GenericSpecificationsBuilder<DigitalSignatureCertificate> specifications = new GenericSpecificationsBuilder<>(filters);
        Page<DigitalSignatureCertificate> page = certificateReadRepository.findAll(specifications, pageable);
        
        return buildPaginatedResponse(page);
    }



    /**
     * Construye la respuesta paginada a partir de la página de resultados
     */
    private PaginatedResponse buildPaginatedResponse(Page<DigitalSignatureCertificate> data) {
        List<DigitalSignatureCertificateResponse> content = data.getContent().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return new PaginatedResponse(content, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    /**
     * Convierte una entidad a Response con información adicional y calculada
     */
    private DigitalSignatureCertificateResponse mapToResponse(DigitalSignatureCertificate entity) {
        LocalDateTime now = LocalDateTime.now();
        boolean expired = entity.getExpirationDate() != null && now.isAfter(entity.getExpirationDate());
        
        // Calcular días hasta expiración
        long daysUntilExpiration = 0;
        if (entity.getExpirationDate() != null) {
            daysUntilExpiration = java.time.temporal.ChronoUnit.DAYS.between(now, entity.getExpirationDate());
        }
        
        // Determinar estado del certificado
        String certificateStatus;
        if (expired) {
            certificateStatus = "EXPIRADO";
        } else if (entity.getIsActive()) {
            certificateStatus = "ACTIVO";
        } else {
            certificateStatus = "INACTIVO";
        }
        
        return DigitalSignatureCertificateResponse.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .certificateName(entity.getCertificateName())
                .certificateP12Base64(entity.getCertificateP12() != null ? 
                    java.util.Base64.getEncoder().encodeToString(entity.getCertificateP12()) : null)
                .isActive(entity.getIsActive())
                .expirationDate(entity.getExpirationDate())
                .businessId(entity.getBusiness() != null ? entity.getBusiness().getId() : null)
                .businessName(entity.getBusiness() != null ? entity.getBusiness().getName() : null)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                // Campos adicionales calculados
                .hasExpired(expired)
                .certificateStatus(certificateStatus)
                .daysUntilExpiration(daysUntilExpiration)
                .build();
    }

    /**
     * Convierte una entidad a DTO
     */
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
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}