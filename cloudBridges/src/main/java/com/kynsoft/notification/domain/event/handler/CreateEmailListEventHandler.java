package com.kynsoft.notification.domain.event.handler;

import com.kynsoft.notification.domain.bean.ImportEmailListRow;
import com.kynsoft.notification.domain.dto.CampaignDto;
import com.kynsoft.notification.domain.dto.EmailListDto;
import com.kynsoft.notification.domain.event.CreateEmailListEvent;
import com.kynsoft.notification.domain.service.CampaignService;
import com.kynsoft.notification.domain.service.EmailListService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class CreateEmailListEventHandler {
    private final EmailListService emailListService;
    private final CampaignService campaignService;

    public CreateEmailListEventHandler(EmailListService emailListService, CampaignService campaignService) {
        this.emailListService = emailListService;
        this.campaignService = campaignService;
    }

    @EventListener
    public void onCreateEmailListEvent(CreateEmailListEvent event) {
        List<ImportEmailListRow> emailListRow = event.getImportEmailListRow();
        CampaignDto campaignDto = campaignService.findById(event.getCampaignId());
        List<EmailListDto> emailListDtos = emailListRow.stream()
                .map(row -> EmailListDto.builder()
                        .email(row.getEmail())
                        .clientName(row.getClientName())
                        .clientLastname(row.getClientLastname())
                        .campaign(campaignDto)
                        .build()
                ).toList();
        emailListService.createBulkEmailList(emailListDtos);
    }
}
