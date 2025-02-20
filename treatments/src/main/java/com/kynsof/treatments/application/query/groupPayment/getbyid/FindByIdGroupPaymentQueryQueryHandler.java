package com.kynsof.treatments.application.query.groupPayment.getbyid;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.treatments.domain.dto.ExamDto;
import com.kynsof.treatments.domain.dto.GroupPaymentDto;
import com.kynsof.treatments.domain.service.IExamService;
import com.kynsof.treatments.domain.service.IGroupPaymentService;
import org.springframework.stereotype.Component;

@Component
public class FindByIdGroupPaymentQueryQueryHandler implements IQueryHandler<FindByIdGroupPaymentQuery, GroupPaymentResponse> {

    private final IGroupPaymentService serviceImpl;

    public FindByIdGroupPaymentQueryQueryHandler(IGroupPaymentService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public GroupPaymentResponse handle(FindByIdGroupPaymentQuery query) {
        GroupPaymentDto object = serviceImpl.findById(query.getId());

        return new GroupPaymentResponse(object);
    }
}
