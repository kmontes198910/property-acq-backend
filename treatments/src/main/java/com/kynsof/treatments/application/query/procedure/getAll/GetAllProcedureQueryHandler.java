//package com.kynsof.treatments.application.query.procedure.getAll;
//
//import com.kynsof.share.core.domain.bus.query.IQueryHandler;
//import com.kynsof.share.core.domain.response.PaginatedResponse;
//import com.kynsof.treatments.domain.service.IProcedureService;
//import org.springframework.stereotype.Component;
//
//@Component
//public class GetAllProcedureQueryHandler implements IQueryHandler<GetAllProcedureQuery, PaginatedResponse>{
//
//    private final IProcedureService serviceImpl;
//
//    public GetAllProcedureQueryHandler(IProcedureService serviceImpl) {
//        this.serviceImpl = serviceImpl;
//    }
//
//    @Override
//    public PaginatedResponse handle(GetAllProcedureQuery query) {
//        return this.serviceImpl.findAll(query.getPageable(), query.getName(), query.getCode(), query.getType());
//    }
//}
