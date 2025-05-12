package com.kynsoft.cirugia.application.query.admision.getbyid;

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
 * Manejador de la consulta de admisión por ID
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class GetAdmisionByIdHandler implements IQueryHandler<GetAdmisionByIdQuery, AdmisionResponse> {

    private final IAdmisionService admisionService;

    @Override
    public AdmisionResponse handle(GetAdmisionByIdQuery query) throws BusinessException {
        log.info("Procesando consulta de admisión por ID: {}", query.getId());
        
        Optional<AdmisionDto> admisionOpt = admisionService.getAdmisionById(query.getId());
        
        if (admisionOpt.isEmpty()) {
            return new AdmisionResponse();
        }
        
        AdmisionDto admisionDto = admisionOpt.get();

        return AdmisionResponse.builder()
                .id(admisionDto.getId())
                .room(admisionDto.getRoom())
                .bed(admisionDto.getBed())
                .observations(admisionDto.getObservations())
                .surgeryId(admisionDto.getSurgeryId())
                .createdBy(admisionDto.getCreatedBy())
                .updatedBy(admisionDto.getUpdatedBy())
                .createdAt(admisionDto.getCreatedAt())
                .updatedAt(admisionDto.getUpdatedAt())
                .build();

    }
}
