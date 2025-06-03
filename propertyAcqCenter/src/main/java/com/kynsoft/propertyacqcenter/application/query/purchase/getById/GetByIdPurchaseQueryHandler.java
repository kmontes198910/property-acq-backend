package com.kynsoft.propertyacqcenter.application.query.purchase.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.PurchaseResponse;
import com.kynsoft.propertyacqcenter.domain.services.IPurchaseService;
import org.springframework.stereotype.Component;

@Component
public class GetByIdPurchaseQueryHandler implements IQueryHandler<GetByIdPurchaseQuery, PurchaseResponse>{

    private final IPurchaseService purchaseService;

    public GetByIdPurchaseQueryHandler(IPurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @Override
    public PurchaseResponse handle(GetByIdPurchaseQuery query) {

        return new PurchaseResponse(this.purchaseService.findById(query.getId()));
    }
}
