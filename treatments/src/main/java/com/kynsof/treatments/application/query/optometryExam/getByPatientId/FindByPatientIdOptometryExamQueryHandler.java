package com.kynsof.treatments.application.query.optometryExam.getByPatientId;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.treatments.application.query.optometryExam.getById.OptometryExamResponse;
import com.kynsof.treatments.domain.dto.OptometryExamDto;
import com.kynsof.treatments.domain.service.IOptometryExamService;
import org.springframework.stereotype.Component;

@Component
public class FindByPatientIdOptometryExamQueryHandler implements IQueryHandler<FindByPatientIdOptometryExamQuery, OptometryExamResponse> {

    private final IOptometryExamService serviceImpl;

    public FindByPatientIdOptometryExamQueryHandler(IOptometryExamService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public OptometryExamResponse handle(FindByPatientIdOptometryExamQuery query) {
        OptometryExamDto contactInfoDto = serviceImpl.getLastCurrentExamByPatient(query.getPatientId());
        if(contactInfoDto == null) {
            return null;
        }
        return new OptometryExamResponse(contactInfoDto);
    }
}
