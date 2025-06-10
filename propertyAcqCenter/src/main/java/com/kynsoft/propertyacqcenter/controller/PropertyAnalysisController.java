package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.allAnalysis.create.CreateAllAnalysisCommand;
import com.kynsoft.propertyacqcenter.application.command.allAnalysis.create.CreateAllAnalysisMessage;
import com.kynsoft.propertyacqcenter.application.command.allAnalysis.create.CreateAllAnalysisRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/property-analysis")
@Slf4j
public class PropertyAnalysisController {

    private final IMediator mediator;

    public PropertyAnalysisController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreateAllAnalysisMessage> createAllergy(@RequestBody CreateAllAnalysisRequest request) {
        CreateAllAnalysisCommand createCommand = CreateAllAnalysisCommand.fromRequest(request);
        CreateAllAnalysisMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }
}
