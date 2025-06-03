package com.kynsoft.propertyacqcenter.application.query.purchase.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.IPurchaseService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchPurchaseQueryHandler implements IQueryHandler<GetSearchPurchaseQuery, PaginatedResponse>{

    private final IPurchaseService purchaseService;

    public GetSearchPurchaseQueryHandler(IPurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @Override
    public PaginatedResponse handle(GetSearchPurchaseQuery query) {

        return this.purchaseService.search(query.getPageable(),query.getFilter());
    }
}
