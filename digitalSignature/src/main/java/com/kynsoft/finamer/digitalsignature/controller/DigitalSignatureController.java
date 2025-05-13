package com.kynsoft.finamer.digitalsignature.controller;

import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.finamer.digitalsignature.application.command.digitalsignaturecertificate.create.DocumentSignCommand;
import com.kynsoft.finamer.digitalsignature.application.command.digitalsignaturecertificate.create.DocumentSignMessage;
import com.kynsoft.finamer.digitalsignature.application.command.digitalsignaturecertificate.create.DocumentSignRequest;
import com.kynsoft.finamer.digitalsignature.domain.dto.*;
import com.kynsoft.finamer.digitalsignature.domain.exception.InvalidSignaturePositionException;
import com.kynsoft.finamer.digitalsignature.domain.service.IDigitalSignatureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sign")
@Tag(name = "Firma Digital", description = "API para firma digital y validación de documentos PDF")
public class DigitalSignatureController {

    private static final Logger logger = LoggerFactory.getLogger(DigitalSignatureController.class);
    
    private final IDigitalSignatureService digitalSignatureService;
    private final IMediator mediator;
    
    @Autowired
    public DigitalSignatureController(IDigitalSignatureService digitalSignatureService, IMediator mediator) {
        this.digitalSignatureService = digitalSignatureService;
        this.mediator = mediator;
    }
    
    /**
     * Firma un documento PDF
     * @param request Datos para la firma
     * @return Documento firmado y metadatos
     */
    @Operation(summary = "Firma un documento PDF", description = "Aplica una firma digital a un documento PDF utilizando un certificado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Documento firmado correctamente", 
                     content = @Content(schema = @Schema(implementation = SignResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Parámetros inválidos o posición de firma inválida"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signDocument(
            @Parameter(description = "Datos necesarios para firmar el documento", required = true) 
            @RequestBody DocumentSignRequest request,
            @RequestHeader(value = "X-User-ID", required = false) String userId){
        logger.info("Recibida solicitud para firmar documento");
        
        try {
            DocumentSignCommand command = DocumentSignCommand.fromRequest(request, userId); // userId debe ser obtenido del contexto de seguridad
            DocumentSignMessage response = mediator.send(command);
            return ResponseEntity.ok(response);
        } catch (InvalidSignaturePositionException e) {
            // Error específico para posición de firma inválida
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .header("X-Error-Code", e.getError().getCode())
                .header("X-Error-Message", e.getError().getMessage())
                .build();
        } catch (Exception e) {
            logger.error("Error al procesar solicitud de firma", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Valida un documento PDF firmado
     * @param request Datos para la validación
     * @return Resultado de la validación y metadatos
     */
    @Operation(summary = "Valida un documento PDF firmado", description = "Verifica la autenticidad de las firmas digitales en un documento PDF")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Documento validado correctamente", 
                     content = @Content(schema = @Schema(implementation = ValidationResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Parámetros inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping(value = "/validate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ValidationResponseDto> validateDocument(
            @Parameter(description = "Datos necesarios para validar el documento", required = true) 
            @RequestBody ValidationRequestDto request) {
        logger.info("Recibida solicitud para validar documento firmado");
        
        try {
            ValidationResponseDto response = digitalSignatureService.validateDocument(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error al procesar solicitud de validación", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}