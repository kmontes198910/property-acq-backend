package com.kynsoft.finamer.digitalsignature.controller;


import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.finamer.digitalsignature.application.command.digitalsignaturecertificate.create.CreateDigitalSignatureCertificateCommand;
import com.kynsoft.finamer.digitalsignature.application.command.digitalsignaturecertificate.create.CreateDigitalSignatureCertificateMessage;
import com.kynsoft.finamer.digitalsignature.application.command.digitalsignaturecertificate.create.CreateDigitalSignatureCertificateRequest;
import com.kynsoft.finamer.digitalsignature.application.command.digitalsignaturecertificate.delete.DeleteDigitalSignatureCertificateCommand;
import com.kynsoft.finamer.digitalsignature.application.command.digitalsignaturecertificate.delete.DeleteDigitalSignatureCertificateMessage;
import com.kynsoft.finamer.digitalsignature.application.command.digitalsignaturecertificate.delete.DeleteDigitalSignatureCertificateRequest;
import com.kynsoft.finamer.digitalsignature.application.command.digitalsignaturecertificate.update.UpdateDigitalSignatureCertificateCommand;
import com.kynsoft.finamer.digitalsignature.application.command.digitalsignaturecertificate.update.UpdateDigitalSignatureCertificateMessage;
import com.kynsoft.finamer.digitalsignature.application.command.digitalsignaturecertificate.update.UpdateDigitalSignatureCertificateRequest;
import com.kynsoft.finamer.digitalsignature.application.query.getbyid.DigitalSignatureCertificateResponse;
import com.kynsoft.finamer.digitalsignature.application.query.getbyid.GetDigitalSignatureCertificateByIdQuery;
import com.kynsoft.finamer.digitalsignature.application.query.search.SearchDigitalSignatureCertificateQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/digital-signatures")
@RequiredArgsConstructor
public class DigitalSignatureCertificateController {

    private final IMediator mediator;
    private static final String USER_ID_HEADER = "X-User-ID";
    private static final String USER_NAME_HEADER = "X-User-Name";

    @PostMapping
    public ResponseEntity<CreateDigitalSignatureCertificateMessage> create(
            @RequestBody CreateDigitalSignatureCertificateRequest request,
            @RequestHeader(USER_ID_HEADER) String userId) {
        
        log.info("Creando nueva firma digital para usuario: {}", request.getUserId());
        
        CreateDigitalSignatureCertificateCommand command = CreateDigitalSignatureCertificateCommand.fromRequest(request, userId);
        CreateDigitalSignatureCertificateMessage response = mediator.send(command);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateDigitalSignatureCertificateMessage> update(
            @PathVariable UUID id,
            @RequestBody UpdateDigitalSignatureCertificateRequest request,
            @RequestHeader(USER_ID_HEADER) String userId) {
        
        log.info("Actualizando firma digital con ID: {}", id);
        
        UpdateDigitalSignatureCertificateCommand command = UpdateDigitalSignatureCertificateCommand.fromRequest(request, id, userId);
        UpdateDigitalSignatureCertificateMessage response = mediator.send(command);
        
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteDigitalSignatureCertificateMessage> delete(
            @PathVariable UUID id,
            @RequestHeader(USER_ID_HEADER) String userId) {
        
        log.info("Eliminando firma digital con ID: {}", id);
        
        DeleteDigitalSignatureCertificateRequest request = new DeleteDigitalSignatureCertificateRequest(id);
        DeleteDigitalSignatureCertificateCommand command = DeleteDigitalSignatureCertificateCommand.fromRequest(request, userId);
        DeleteDigitalSignatureCertificateMessage response = mediator.send(command);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DigitalSignatureCertificateResponse> getById(@PathVariable UUID id) {
        log.info("Obteniendo firma digital con ID: {}", id);
        
        GetDigitalSignatureCertificateByIdQuery query = new GetDigitalSignatureCertificateByIdQuery(id);
        DigitalSignatureCertificateResponse response = mediator.send(query);
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<?> search(
            @RequestBody SearchRequest request,
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) UUID businessId) {
        
        log.info("Buscando firmas digitales con filtros");

        Pageable pageable = PageableUtil.createPageable(request);
        
        SearchDigitalSignatureCertificateQuery query = new SearchDigitalSignatureCertificateQuery(
                pageable, 
                request.getFilter(), 
                request.getQuery(), 
                businessId, 
                userId);
        
        PaginatedResponse response = mediator.send(query);
        
        return ResponseEntity.ok(response);
    }

}
