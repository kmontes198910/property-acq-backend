package com.kynsof.evaluation.application.query.doctor.getByIdHttp;

import com.kynsof.evaluation.domain.service.IDoctorService;
import com.kynsof.evaluation.infrastructure.service.http.DoctorHttpUUIDService;
import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.http.entity.DoctorHttp;
import org.springframework.stereotype.Component;

@Component
public class FindHttpDoctorByIdQueryHandler implements IQueryHandler<FindHttpDoctorByIdQuery, DoctorHttp>  {

    private final DoctorHttpUUIDService serviceImpl;
    private final IDoctorService doctorService;

    public FindHttpDoctorByIdQueryHandler(DoctorHttpUUIDService serviceImpl,
                                          IDoctorService doctorService) {
        this.serviceImpl = serviceImpl;
        this.doctorService = doctorService;
    }

    @Override
    public DoctorHttp handle(FindHttpDoctorByIdQuery query) {
        DoctorHttp doctor = serviceImpl.sendGetHttpRequest(query.getId());
        return doctor;
    }
}
