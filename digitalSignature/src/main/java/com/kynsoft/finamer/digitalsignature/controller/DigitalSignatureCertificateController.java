package com.kynsoft.finamer.digitalsignature.controller;


import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.finamer.digitalsignature.application.command.digitalsignaturecertificate.create.DocumentSignMessage;
import com.kynsoft.finamer.digitalsignature.application.command.digitalsignaturecertificate.delete.DeleteDigitalSignatureCertificateCommand;
import com.kynsoft.finamer.digitalsignature.application.command.digitalsignaturecertificate.delete.DeleteDigitalSignatureCertificateMessage;
import com.kynsoft.finamer.digitalsignature.application.command.digitalsignaturecertificate.delete.DeleteDigitalSignatureCertificateRequest;
import com.kynsoft.finamer.digitalsignature.application.command.digitalsignaturecertificate.update.UpdateDigitalSignatureCertificateCommand;
import com.kynsoft.finamer.digitalsignature.application.command.digitalsignaturecertificate.update.UpdateDigitalSignatureCertificateMessage;
import com.kynsoft.finamer.digitalsignature.application.command.digitalsignaturecertificate.update.UpdateDigitalSignatureCertificateRequest;
import com.kynsoft.finamer.digitalsignature.application.command.sing.CreateDigitalSignatureCertificateCommand;
import com.kynsoft.finamer.digitalsignature.application.command.sing.CreateDigitalSignatureCertificateMessage;
import com.kynsoft.finamer.digitalsignature.application.command.sing.CreateDigitalSignatureCertificateRequest;
import com.kynsoft.finamer.digitalsignature.application.query.getbyid.DigitalSignatureCertificateResponse;
import com.kynsoft.finamer.digitalsignature.application.query.getbyid.GetDigitalSignatureCertificateByIdQuery;
import com.kynsoft.finamer.digitalsignature.application.query.search.SearchDigitalSignatureCertificateQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Certificados", description = "Operaciones con certificados de firma digital")
public class DigitalSignatureCertificateController {

    private final IMediator mediator;
    private static final String USER_ID_HEADER = "X-User-ID";
    private static final String USER_NAME_HEADER = "X-User-Name";

    @Operation(summary = "Crear un nuevo certificado de firma digital", 
               description = "Registra un nuevo certificado de firma digital para un usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Certificado creado correctamente",
                     content = @Content(schema = @Schema(implementation = DocumentSignMessage.class))),
        @ApiResponse(responseCode = "400", description = "Datos de certificado inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<?> create(
            @Parameter(description = "Datos del certificado a crear", required = true)
            @RequestBody CreateDigitalSignatureCertificateRequest request,
            @Parameter(description = "ID del usuario que realiza la operación", required = true)
            @RequestHeader(USER_ID_HEADER) String userId) {
        
        log.info("Creando nueva firma digital para usuario: {}", userId);
        
        CreateDigitalSignatureCertificateCommand command = CreateDigitalSignatureCertificateCommand.fromRequest(request, userId);
        CreateDigitalSignatureCertificateMessage response = mediator.send(command);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Actualizar un certificado de firma digital", 
               description = "Actualiza un certificado de firma digital existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Certificado actualizado correctamente",
                     content = @Content(schema = @Schema(implementation = UpdateDigitalSignatureCertificateMessage.class))),
        @ApiResponse(responseCode = "400", description = "Datos de certificado inválidos"),
        @ApiResponse(responseCode = "404", description = "Certificado no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UpdateDigitalSignatureCertificateMessage> update(
            @Parameter(description = "ID del certificado a actualizar", required = true)
            @PathVariable UUID id,
            @Parameter(description = "Datos actualizados del certificado", required = true)
            @RequestBody UpdateDigitalSignatureCertificateRequest request,
            @Parameter(description = "ID del usuario que realiza la operación", required = true)
            @RequestHeader(USER_ID_HEADER) String userId) {
        
        log.info("Actualizando firma digital con ID: {}", id);
        
        UpdateDigitalSignatureCertificateCommand command = UpdateDigitalSignatureCertificateCommand.fromRequest(request, id, userId);
        UpdateDigitalSignatureCertificateMessage response = mediator.send(command);
        
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar un certificado de firma digital", 
               description = "Elimina un certificado de firma digital existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Certificado eliminado correctamente",
                     content = @Content(schema = @Schema(implementation = DeleteDigitalSignatureCertificateMessage.class))),
        @ApiResponse(responseCode = "404", description = "Certificado no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteDigitalSignatureCertificateMessage> delete(
            @Parameter(description = "ID del certificado a eliminar", required = true)
            @PathVariable UUID id,
            @Parameter(description = "ID del usuario que realiza la operación", required = true)
            @RequestHeader(USER_ID_HEADER) String userId) {
        
        log.info("Eliminando firma digital con ID: {}", id);
        
        DeleteDigitalSignatureCertificateRequest request = new DeleteDigitalSignatureCertificateRequest(id);
        DeleteDigitalSignatureCertificateCommand command = DeleteDigitalSignatureCertificateCommand.fromRequest(request, userId);
        DeleteDigitalSignatureCertificateMessage response = mediator.send(command);
        
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener un certificado de firma digital por ID", 
               description = "Recupera la información de un certificado de firma digital específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Certificado recuperado correctamente",
                     content = @Content(schema = @Schema(implementation = DigitalSignatureCertificateResponse.class))),
        @ApiResponse(responseCode = "404", description = "Certificado no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DigitalSignatureCertificateResponse> getById(
            @Parameter(description = "ID del certificado a recuperar", required = true)
            @PathVariable UUID id) {
        log.info("Obteniendo firma digital con ID: {}", id);
        
        GetDigitalSignatureCertificateByIdQuery query = new GetDigitalSignatureCertificateByIdQuery(id);
        DigitalSignatureCertificateResponse response = mediator.send(query);
        
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Buscar certificados de firma digital", 
               description = "Busca certificados de firma digital con criterios de filtrado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Búsqueda realizada correctamente",
                     content = @Content(schema = @Schema(implementation = PaginatedResponse.class))),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/search")
    public ResponseEntity<?> search(
            @Parameter(description = "Parámetros de búsqueda y paginación", required = true)
            @RequestBody SearchRequest request) {
        
        log.info("Buscando firmas digitales con filtros");

        Pageable pageable = PageableUtil.createPageable(request);
        
        SearchDigitalSignatureCertificateQuery query = new SearchDigitalSignatureCertificateQuery(
                pageable, 
                request.getFilter(), 
                request.getQuery());
        
        PaginatedResponse response = mediator.send(query);
        
        return ResponseEntity.ok(response);
    }
}
