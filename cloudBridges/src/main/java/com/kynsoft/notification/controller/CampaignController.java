package com.kynsoft.notification.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.notification.application.command.campaign.create.CreateCampaignCommand;
import com.kynsoft.notification.application.command.campaign.create.CreateCampaignRequest;
import com.kynsoft.notification.application.command.campaign.send.SendCampaignRequest;
import com.kynsoft.notification.application.command.campaign.send.SendEmailCampaignCommand;
import com.kynsoft.notification.application.command.campaign.update.UpdateCampaignCommand;
import com.kynsoft.notification.application.command.campaign.update.UpdateCampaignRequest;
import com.kynsoft.notification.application.query.campaign.getAllCampaign.GetAllCampaignQuery;
import com.kynsoft.notification.application.query.campaign.getById.GetCampaignByIdQuery;
import com.kynsoft.notification.application.query.campaign.search.SearchCampaignQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/campaign")
public class CampaignController {

    private final IMediator mediator;

    public CampaignController(IMediator mediator) {
        this.mediator = mediator;
    }


    @PostMapping
    public ResponseEntity<?> createCampaign(@RequestBody CreateCampaignRequest createCampaignRequest) {
        CreateCampaignCommand createCampaignCommand = new CreateCampaignCommand(createCampaignRequest);
        return ResponseEntity.ok(mediator.send(createCampaignCommand));
    }

    @PatchMapping()
    public ResponseEntity<?> updateCampaign(@RequestBody UpdateCampaignRequest updateCampaignRequest){
        UpdateCampaignCommand updateCampaignCommand = new UpdateCampaignCommand(updateCampaignRequest);
        return ResponseEntity.ok(mediator.send(updateCampaignCommand));
    }

    @GetMapping()
    public ResponseEntity<?> getCampaign(Pageable pageable){
        GetAllCampaignQuery campaignQuery = new GetAllCampaignQuery(pageable);
        return ResponseEntity.ok(mediator.send(campaignQuery));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getCampaignById(@PathVariable("id")String id){
        GetCampaignByIdQuery query = new GetCampaignByIdQuery(id);
        return ResponseEntity.ok(mediator.send(query));
    }

    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody SearchRequest searchRequest){
        SearchCampaignQuery searchCampaignQuery = new SearchCampaignQuery(
                PageableUtil.createPageable(searchRequest),
                searchRequest.getFilter(),
                searchRequest.getQuery());
        return ResponseEntity.ok(mediator.send(searchCampaignQuery));
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendEmailCampaign(@RequestBody SendCampaignRequest sendCampaignRequest){
        SendEmailCampaignCommand sendEmailCampaignCommand = new SendEmailCampaignCommand(sendCampaignRequest);
        return ResponseEntity.ok(mediator.send(sendEmailCampaignCommand));
    }
}
