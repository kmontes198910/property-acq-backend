package com.kynsoft.cirugia.application.query.doctor.getbyid;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.cirugia.application.response.DoctorResponse;
import com.kynsoft.cirugia.domain.dto.DoctorDto;
import com.kynsoft.cirugia.domain.service.IDoctorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GetDoctorByIdQueryHandler implements IQueryHandler<GetDoctorByIdQuery, DoctorResponse> {

    private final IDoctorService service;

    public GetDoctorByIdQueryHandler(IDoctorService service) {
        this.service = service;
    }

    @Override
    public DoctorResponse handle(GetDoctorByIdQuery query) {
        DoctorDto doctor = service.findById(query.getId());

        return new DoctorResponse(doctor);
    }
}