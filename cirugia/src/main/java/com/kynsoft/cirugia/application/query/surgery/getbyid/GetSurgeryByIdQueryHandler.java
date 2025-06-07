package com.kynsoft.cirugia.application.query.surgery.getbyid;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DoctorIsNotPermisionNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsoft.cirugia.application.query.surgery.SurgeryResponse;
import com.kynsoft.cirugia.domain.service.IMedicalTeamService;
import com.kynsoft.cirugia.domain.service.ISurgeryService;
import com.kynsoft.cirugia.infrastructure.entities.SurgeryEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetSurgeryByIdQueryHandler implements IQueryHandler<GetSurgeryByIdQuery, SurgeryResponse> {

    private final ISurgeryService service;
    private final IMedicalTeamService medicalTeamService;

    @Override
    @Transactional(readOnly = true)
    public SurgeryResponse handle(GetSurgeryByIdQuery query) {
        log.info("Finding surgery with ID: {}", query.getId());


        Optional<SurgeryEntity> surgeryEntity = service.getSurgeryEntityById(query.getId());
        
        if (surgeryEntity.isEmpty()) {
            throw new BusinessNotFoundException(
                new GlobalBusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND, 
                new ErrorField("id", "Surgery not found with ID: " + query.getId())));
        }
        boolean isAuthorized = medicalTeamService.isMemberInSurgeryTeam(query.getId(), query.getUserId());

//        if (!isAuthorized) {
//            throw new DoctorIsNotPermisionNotFoundException(
//                new GlobalBusinessException(DomainErrorMessage.ESPECIALITY_IS_NOT_ACCESS,
//                new ErrorField("userId", "El usuario no tiene permiso para acceder a esta cirugía.")));
//        }

        return new SurgeryResponse(surgeryEntity.get());
    }
}