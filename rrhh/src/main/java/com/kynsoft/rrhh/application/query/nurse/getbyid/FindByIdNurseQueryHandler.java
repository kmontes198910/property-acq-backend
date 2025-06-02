package com.kynsoft.rrhh.application.query.nurse.getbyid;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.rrhh.domain.dto.NurseDto;
import com.kynsoft.rrhh.domain.interfaces.services.INurseService;
import org.springframework.stereotype.Component;

@Component
public class FindByIdNurseQueryHandler implements IQueryHandler<FindByIdNurseQuery, NurseResponse>  {

    private final INurseService serviceImpl;

    public FindByIdNurseQueryHandler(INurseService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public NurseResponse handle(FindByIdNurseQuery query) {
        NurseDto nurseDto = serviceImpl.findById(query.getId());
        return new NurseResponse(nurseDto);
    }
}