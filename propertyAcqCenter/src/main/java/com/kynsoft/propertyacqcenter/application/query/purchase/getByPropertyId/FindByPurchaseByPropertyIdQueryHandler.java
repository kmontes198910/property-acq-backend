package com.kynsoft.propertyacqcenter.application.query.purchase.getByPropertyId;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.PurchaseResponse;
import com.kynsoft.propertyacqcenter.domain.services.IPurchaseService;
import org.springframework.stereotype.Component;

@Component
public class FindByPurchaseByPropertyIdQueryHandler implements IQueryHandler<FindByPurchaseByPropertyIdQuery, PurchaseResponse> {

    private final IPurchaseService purchaseService;

    public FindByPurchaseByPropertyIdQueryHandler(IPurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @Override
    public PurchaseResponse handle(FindByPurchaseByPropertyIdQuery query) {
        return new PurchaseResponse(this.purchaseService.findByPropertyId(query.getId()));
    }
}
