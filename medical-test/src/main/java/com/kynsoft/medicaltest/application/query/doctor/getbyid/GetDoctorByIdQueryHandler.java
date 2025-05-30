package com.kynsoft.medicaltest.application.query.doctor.getbyid;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.medicaltest.domain.dto.DoctorDto;
import com.kynsoft.medicaltest.domain.service.IDoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetDoctorByIdQueryHandler implements IQueryHandler<GetDoctorByIdQuery, DoctorResponse> {

    private final IDoctorService doctorService;

    @Override
    public DoctorResponse handle(GetDoctorByIdQuery query) {
        log.info("Buscando examen de laboratorio con ID: {}", query.getId());

        DoctorDto doc = doctorService.findById(query.getId());
        
        return new DoctorResponse(doc);
    }
}
