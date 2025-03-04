package com.kynsoft.rrhh.application.query.doctor.getByIdHttpReplicate;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.http.entity.DoctorHttp;
import com.kynsoft.rrhh.domain.dto.DoctorDto;
import com.kynsoft.rrhh.domain.interfaces.services.IDoctorService;
import org.springframework.stereotype.Component;

@Component
public class FindHttpDoctorByIdQueryHandler implements IQueryHandler<FindHttpDoctorByIdQuery, DoctorHttp>  {

    private final IDoctorService serviceImpl;

    public FindHttpDoctorByIdQueryHandler(IDoctorService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public DoctorHttp handle(FindHttpDoctorByIdQuery query) {
        DoctorDto doctor = serviceImpl.findById(query.getId());
        return new DoctorHttp(
                doctor.getId(), 
                doctor.getIdentification(), 
                doctor.getName(), 
                doctor.getLastName(), 
                doctor.getRegisterNumber(), 
                doctor.getStatus(), 
                doctor.getImage()
        );
    }
}
