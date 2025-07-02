package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.titleCompany.UpdateAdquisitionPropertyTitleCompanyCommand;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.titleCompany.UpdateAdquisitionPropertyTitleCompanyMessage;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.titleCompany.UpdateAdquisitionPropertyTitleCompanyRequest;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/title-company-acquisition-property")
public class TitleCompanyAdquisitionPropertyController {

    private final IMediator mediator;

    public TitleCompanyAdquisitionPropertyController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateAdquisitionPropertyTitleCompanyRequest request) {

        UpdateAdquisitionPropertyTitleCompanyCommand command = UpdateAdquisitionPropertyTitleCompanyCommand.fromRequest(request, id);
        UpdateAdquisitionPropertyTitleCompanyMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

}
