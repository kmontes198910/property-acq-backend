package com.kynsoft.cirugia.application.query.admision.getbysurgeryid;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.exception.BusinessException;

import com.kynsoft.cirugia.application.query.admision.AdmisionResponse;
import com.kynsoft.cirugia.domain.dto.AdmisionDto;
import com.kynsoft.cirugia.domain.service.IAdmisionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Handler for the admission query by surgery ID
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class GetAdmisionBySurgeryIdHandler implements IQueryHandler<GetAdmisionBySurgeryIdQuery, AdmisionResponse> {

    private final IAdmisionService admisionService;

    @Override
    public AdmisionResponse handle(GetAdmisionBySurgeryIdQuery query) throws BusinessException {
        log.info("Processing admission query by surgery ID: {}", query.getSurgeryId());
        
        Optional<AdmisionDto> admisionOpt = admisionService.getAdmisionBySurgeryId(query.getSurgeryId());
        
        if (admisionOpt.isEmpty()) {
            return new AdmisionResponse();
        }
        
        AdmisionDto admisionDto = admisionOpt.get();
        
        // Map DTO to response

        return AdmisionResponse.builder()
                .id(admisionDto.getId())
                .roomId(admisionDto.getRoom())
                .bedId(admisionDto.getBed())
                .observations(admisionDto.getObservations())
                .surgeryId(admisionDto.getSurgeryId())
                .createdBy(admisionDto.getCreatedBy())
                .updatedBy(admisionDto.getUpdatedBy())
                .createdAt(admisionDto.getCreatedAt())
                .updatedAt(admisionDto.getUpdatedAt())
                .build();
    }
}
