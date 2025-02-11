package com.kynsoft.notification.application.query.campaign.getById;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.notification.domain.dto.CampaignDto;
import lombok.Getter;

@Getter
public class GetCampaignByIdResponse implements IResponse {

    private final CampaignDto campaignDto;

    public GetCampaignByIdResponse(CampaignDto campaignDto) {
        this.campaignDto = campaignDto;
    }
}
