package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.seller.UpdateAdquisitionPropertySellerCommand;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.seller.UpdateAdquisitionPropertySellerMessage;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.seller.UpdateAdquisitionPropertySellerRequest;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seller-acquisition-property")
public class SellerAdquisitionPropertyController {

    private final IMediator mediator;

    public SellerAdquisitionPropertyController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id, @RequestBody UpdateAdquisitionPropertySellerRequest request) {

        UpdateAdquisitionPropertySellerCommand command = UpdateAdquisitionPropertySellerCommand.fromRequest(request, id);
        UpdateAdquisitionPropertySellerMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

}
