package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.analysis.create.AnalysisRequest;
import com.kynsoft.propertyacqcenter.application.command.analysis.create.CreateAnalysisCommand;
import com.kynsoft.propertyacqcenter.application.command.analysis.create.CreateAnalysisMessage;
import com.kynsoft.propertyacqcenter.application.command.analysis.delete.DeleteAnalysisCommand;
import com.kynsoft.propertyacqcenter.application.command.analysis.delete.DeleteAnalysisMessage;
import com.kynsoft.propertyacqcenter.application.query.analysis.search.GetSearchAnalysisQuery;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analysis")
@Slf4j
public class AnalysisController {

    private final IMediator mediator;
    private static final String USER_ID_HEADER = "X-User-ID";

    public AnalysisController(IMediator mediator) {

        this.mediator = mediator;
    }

    @PostMapping("")
    public ResponseEntity<CreateAnalysisMessage> createAllergy(@RequestBody AnalysisRequest request,
                                                               @RequestHeader(value = USER_ID_HEADER, required = false) UUID userId) {
        logUserInfo(userId);
        CreateAnalysisCommand createCommand = CreateAnalysisCommand.fromRequest(request, userId);
        CreateAnalysisMessage response = mediator.send(createCommand);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteAnalysisCommand command = new DeleteAnalysisCommand(id);
        DeleteAnalysisMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request)
    {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchAnalysisQuery query = new GetSearchAnalysisQuery(pageable, request.getFilter(),request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

    /**
     * Método auxiliar para registrar información del usuario en los logs
     */
    private void logUserInfo(UUID userId) {
        if (userId != null) {
            log.info("Request from user: {} (ID: {})", userId);
        } else if (userId != null) {
            log.info("Request from user ID: {}", userId);
        }
    }
}
