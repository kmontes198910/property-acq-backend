package com.kynsoft.cirugia.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.cirugia.application.command.diagnosis.create.CreateDiagnosisCommand;
import com.kynsoft.cirugia.application.command.diagnosis.create.CreateDiagnosisMessage;
import com.kynsoft.cirugia.application.command.diagnosis.create.CreateDiagnosisRequest;
import com.kynsoft.cirugia.application.command.diagnosis.delete.DeleteDiagnosisCommand;
import com.kynsoft.cirugia.application.query.diagnosis.getbyid.GetDiagnosisByIdQuery;
import com.kynsoft.cirugia.application.query.diagnosis.getbyid.DiagnosisResponse;
import com.kynsoft.cirugia.application.query.diagnosis.listbysurgery.ListDiagnosesBySurgeryQuery;
import com.kynsoft.cirugia.application.query.diagnosis.listbysurgery.DiagnosisListResponse;
import com.kynsoft.cirugia.application.query.diagnosis.search.SearchDiagnosesQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/diagnoses")
@RequiredArgsConstructor
public class DiagnosisController {

    private final IMediator mediator;
    private static final String USER_ID_HEADER = "X-User-ID";
    private static final String USER_NAME_HEADER = "X-User-Name";

    @GetMapping("/{id}")
    public ResponseEntity<DiagnosisResponse> getById(@PathVariable UUID id) {
        GetDiagnosisByIdQuery query = new GetDiagnosisByIdQuery(id);
        DiagnosisResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/surgery/{surgeryId}")
    public ResponseEntity<DiagnosisListResponse> findBySurgeryId(@PathVariable UUID surgeryId) {
        ListDiagnosesBySurgeryQuery query = new ListDiagnosesBySurgeryQuery(surgeryId);
        DiagnosisListResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody CreateDiagnosisRequest request,
                                    @RequestHeader(value = USER_ID_HEADER) String userId,
                                    @RequestHeader(value = USER_NAME_HEADER) String userName) {
        CreateDiagnosisCommand command = CreateDiagnosisCommand.fromRequest(request, UUID.fromString(userId));
        CreateDiagnosisMessage resposne = mediator.send(command);
        return ResponseEntity.ok(resposne);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        mediator.send(new DeleteDiagnosisCommand(id));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request) {
        SearchDiagnosesQuery query = new SearchDiagnosesQuery(
                PageableUtil.createPageable(request), 
                request.getFilter(),
                request.getQuery()
        );
        PaginatedResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }
}