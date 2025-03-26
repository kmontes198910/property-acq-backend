//package com.kynsof.treatments.application.query.externalConsultation.getall;
//
//import com.kynsof.share.core.domain.bus.query.IQueryHandler;
//import com.kynsof.share.core.domain.response.PaginatedResponse;
//import com.kynsof.treatments.domain.service.IExternalConsultationService;
//import org.springframework.stereotype.Component;
//
//@Component
//public class GetAllExternalConsultationQueryHandler implements IQueryHandler<GetAllExternalConsultationQuery, PaginatedResponse> {
//
//    private final IExternalConsultationService serviceImpl;
//
//    public GetAllExternalConsultationQueryHandler(IExternalConsultationService serviceImpl) {
//        this.serviceImpl = serviceImpl;
//    }
//
//    @Override
//    public PaginatedResponse handle(GetAllExternalConsultationQuery query) {
//
//        return this.serviceImpl.findAll(query.getPageable(), query.getDoctorId(), query.getPatientId());
//    }
//}
