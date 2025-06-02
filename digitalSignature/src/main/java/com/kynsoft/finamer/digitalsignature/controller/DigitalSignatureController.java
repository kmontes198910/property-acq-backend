package com.kynsoft.finamer.digitalsignature.controller;

import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.finamer.digitalsignature.application.command.digitalsignaturecertificate.create.DocumentSignCommand;
import com.kynsoft.finamer.digitalsignature.application.command.digitalsignaturecertificate.create.DocumentSignMessage;
import com.kynsoft.finamer.digitalsignature.application.command.digitalsignaturecertificate.create.DocumentSignRequest;
import com.kynsoft.finamer.digitalsignature.application.command.digitalsignaturecertificate.create.VisibleSignatureRequest;
import com.kynsoft.finamer.digitalsignature.domain.dto.*;
import com.kynsoft.finamer.digitalsignature.domain.exception.InvalidSignaturePositionException;
import com.kynsoft.finamer.digitalsignature.domain.service.IDigitalSignatureService;
import com.kynsoft.finamer.digitalsignature.model.dto.ErrorResponseDto;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.UUID;

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
     * @param document Archivo PDF a firmar
     * @param documentName Nombre del documento
     * @param certificatePassword Contraseña del certificado
     * @param certificateP12Id ID del certificado en la base de datos
     * @param reason Razón de la firma
     * @param location Ubicación de la firma
     * @param page Página donde se aplicará la firma
     * @param x Posición X de la firma
     * @param y Posición Y de la firma
     * @param width Ancho de la firma
     * @param height Alto de la firma
     * @param businessId ID del negocio
     * @param returnType Tipo de retorno (pdf/json)
     * @return Documento firmado o metadatos en JSON
     */
    @Operation(summary = "Firma un documento PDF", description = "Aplica una firma digital a un documento PDF utilizando un certificado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Documento firmado correctamente", 
                     content = @Content(schema = @Schema(implementation = SignResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Parámetros inválidos o posición de firma inválida"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> signDocument(
            @Parameter(description = "Archivo PDF a firmar", required = true) 
            @RequestPart("document") MultipartFile document,
            
            @Parameter(description = "Nombre del documento para referencia", required = false) 
            @RequestParam(required = false) String documentName,
            
            @Parameter(description = "Contraseña del certificado", required = false) 
            @RequestParam(required = false) String certificatePassword,
            
            @Parameter(description = "ID del certificado en la base de datos", required = false) 
            @RequestParam(required = false) String certificateP12Id,
            
            @Parameter(description = "Razón de la firma", required = false) 
            @RequestParam(required = false) String reason,
            
            @Parameter(description = "Ubicación de la firma", required = false) 
            @RequestParam(required = false) String location,
            
            @Parameter(description = "Página donde se aplicará la firma visible", required = false) 
            @RequestParam(required = false) Integer page,
            
            @Parameter(description = "Posición X de la firma visible", required = false) 
            @RequestParam(required = false) Float x,
            
            @Parameter(description = "Posición Y de la firma visible", required = false) 
            @RequestParam(required = false) Float y,
            
            @Parameter(description = "Ancho de la firma visible", required = false) 
            @RequestParam(required = false) Float width,
            
            @Parameter(description = "Alto de la firma visible", required = false) 
            @RequestParam(required = false) Float height,
            
            @Parameter(description = "ID del negocio", required = false) 
            @RequestParam(required = false) UUID businessId,
            
            @Parameter(description = "Tipo de retorno (pdf o json)", required = false)
            @RequestParam(required = false, defaultValue = "json") String returnType,
            
            @RequestHeader(value = "X-User-ID", required = false) String userId){
        logger.info("Recibida solicitud para firmar documento");
        
        try {
            // Crear objeto de firma visible si se proporcionaron parámetros de posición
            VisibleSignatureRequest visibleSignature = null;
            if (page != null && x != null && y != null) {
                visibleSignature = new VisibleSignatureRequest();
                visibleSignature.setPage(page);
                visibleSignature.setX(x);
                visibleSignature.setY(y);
                visibleSignature.setWidth(width);
                visibleSignature.setHeight(height);
            }
            
            // Convertir MultipartFile a base64
            String documentBase64 = null;
            try {
                byte[] documentBytes = document.getBytes();
                documentBase64 = Base64.getEncoder().encodeToString(documentBytes);
            } catch (Exception e) {
                logger.error("Error al procesar el archivo: {}", e.getMessage(), e);
                throw new IllegalArgumentException("Error al procesar el archivo: " + e.getMessage());
            }
            
            // Si no se proporciona un nombre de documento, usar el nombre original del archivo
            if (documentName == null || documentName.isEmpty()) {
                documentName = document.getOriginalFilename();
            }
            
            // Crear el objeto de solicitud con los parámetros recibidos
            DocumentSignRequest request = DocumentSignRequest.builder()
                    .document(documentBase64)
                    .documentName(documentName)
                    .certificatePassword(certificatePassword)
                    .certificateP12Id(certificateP12Id)
                    .visibleSignature(visibleSignature)
                    .reason(reason)
                    .location(location)
                    .businessId(businessId)
                    .build();
            
            DocumentSignCommand command = DocumentSignCommand.fromRequest(request, userId);
            DocumentSignMessage response = mediator.send(command);
            
            // Determinar si devolvemos el PDF directamente o los metadatos como JSON
            if ("pdf".equalsIgnoreCase(returnType)) {
                // Configurar headers para descarga de PDF
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                
                // Configurar nombre de archivo para descarga
                String filename = documentName != null ? documentName : "documento_firmado.pdf";
                if (!filename.toLowerCase().endsWith(".pdf")) {
                    filename += ".pdf";
                }
                headers.setContentDispositionFormData("attachment", filename);
                
                // Devolver el documento PDF firmado
                return ResponseEntity.ok()
                        .headers(headers)
                        .body(response.getSignedDocument());
            } else {
                // Devolver respuesta JSON con metadatos
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(response);
            }
        } catch (InvalidSignaturePositionException e) {
            // Error específico para posición de firma inválida
            logger.error("Error en posición de firma: {}", e.getMessage());
            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorResponseDto(e.getError().getCode(), e.getError().getMessage(), false));
        } catch (Exception e) {
            logger.error("Error al procesar solicitud de firma", e);
            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorResponseDto("ERROR-SIGN-001", "Error al procesar la firma: " + e.getMessage(), false));
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
    public ResponseEntity<?> validateDocument(
            @Parameter(description = "Datos necesarios para validar el documento", required = true) 
            @RequestBody ValidationRequestDto request) {
        logger.info("Recibida solicitud para validar documento firmado");
        
        try {
            ValidationResponseDto response = digitalSignatureService.validateDocument(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error al procesar solicitud de validación", e);
            return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorResponseDto("ERROR-VALIDATION-001", "Error al validar el documento: " + e.getMessage(), false));
        }
    }
}