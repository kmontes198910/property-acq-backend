package com.kynsoft.propertyacqcenter.application.query.manageRole.getByEmployeeId;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.PurchaseResponse;
import com.kynsoft.propertyacqcenter.domain.services.IPurchaseService;
import org.springframework.stereotype.Component;

@Component
public class FindRolesByEmployeeIdQueryHandler implements IQueryHandler<FindRolesByEmployeeIdQuery, PurchaseResponse> {

    private final IPurchaseService purchaseService;

    public FindRolesByEmployeeIdQueryHandler(IPurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @Override
    public PurchaseResponse handle(FindRolesByEmployeeIdQuery query) {
        return new PurchaseResponse(this.purchaseService.findByPropertyId(query.getId()));
    }
}
