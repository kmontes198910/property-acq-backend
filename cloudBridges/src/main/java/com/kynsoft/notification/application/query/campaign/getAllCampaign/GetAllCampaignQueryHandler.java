package com.kynsoft.notification.application.query.campaign.getAllCampaign;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.stereotype.Component;

@Component
public class GetAllCampaignQueryHandler implements IQueryHandler<GetAllCampaignQuery, PaginatedResponse> {

    @Override
    public PaginatedResponse handle(GetAllCampaignQuery query) {
        return null;
    }
}
