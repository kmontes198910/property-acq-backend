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
import com.kynsoft.finamer.digitalsignature.model.dto.ErrorResponse;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
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
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(
            @Parameter(description = "ID del usuario asociado al certificado", required = true)
            @RequestParam UUID userId,
            @Parameter(description = "Nombre descriptivo del certificado", required = true)
            @RequestParam String certificateName,
            @Parameter(description = "Archivo de certificado P12", required = true)
            @RequestPart("certificateFile") MultipartFile certificateFile,
            @Parameter(description = "Contraseña del certificado", required = true)
            @RequestParam String certificatePassword,
            @Parameter(description = "Fecha de expiración del certificado", required = false)
            @RequestParam(required = false) String expirationDate,
            @Parameter(description = "Indica si es la clave primaria", required = false)
            @RequestParam(required = false) Boolean isPrimaryKey,
            @Parameter(description = "ID del negocio asociado", required = false)
            @RequestParam(required = false) UUID businessId,
            @Parameter(description = "ID del usuario que realiza la operación", required = true)
            @RequestHeader(USER_ID_HEADER) String userIdHeader) {
        
        log.info("Creando nueva firma digital para usuario: {}", userIdHeader);
        
        try {
            // Convertir el MultipartFile a Base64
            String certificateP12Base64 = Base64.getEncoder().encodeToString(certificateFile.getBytes());
            LocalDateTime dateTime = LocalDate.parse(expirationDate).atStartOfDay();
            // Construir el objeto request con los parámetros recibidos
            CreateDigitalSignatureCertificateRequest request = CreateDigitalSignatureCertificateRequest.builder()
                    .userId(userId)
                    .certificateName(certificateName)
                    .certificateP12Base64(certificateP12Base64)
                    .certificatePassword(certificatePassword)
                    .expirationDate(dateTime)
                    .isPrimaryKey(isPrimaryKey)
                    .businessId(businessId)
                    .build();
            
            CreateDigitalSignatureCertificateCommand command = CreateDigitalSignatureCertificateCommand.fromRequest(request, userIdHeader);
            CreateDigitalSignatureCertificateMessage response = mediator.send(command);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            log.error("Error al procesar archivo de certificado: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("ERROR-CERT-001", "Error al procesar el archivo de certificado: " + e.getMessage(), false));
        }
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
    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(
            @Parameter(description = "ID del certificado a actualizar", required = true)
            @PathVariable UUID id,
            @Parameter(description = "Nombre descriptivo del certificado", required = false)
            @RequestParam(required = false) String certificateName,
            @Parameter(description = "Archivo de certificado P12", required = false)
            @RequestPart(value = "certificateFile", required = false) MultipartFile certificateFile,
            @Parameter(description = "Contraseña del certificado", required = false)
            @RequestParam(required = false) String certificatePassword,
            @Parameter(description = "Fecha de expiración del certificado", required = false)
            @RequestParam(required = false) String expirationDate,
            @Parameter(description = "Estado activo del certificado", required = false)
            @RequestParam(required = false) Boolean isActive,
            @Parameter(description = "Indica si es la clave primaria", required = false)
            @RequestParam(required = false) Boolean isPrimaryKey,
            @Parameter(description = "ID del negocio asociado", required = false)
            @RequestParam(required = false) UUID businessId,
            @Parameter(description = "ID del usuario que realiza la operación", required = true)
            @RequestHeader(USER_ID_HEADER) String userId) {
        
        log.info("Actualizando firma digital con ID: {}", id);
        
        try {
            // Convertir el MultipartFile a Base64 si se proporciona
            String certificateP12Base64 = null;
            if (certificateFile != null && !certificateFile.isEmpty()) {
                certificateP12Base64 = Base64.getEncoder().encodeToString(certificateFile.getBytes());
            }
            LocalDateTime dateTime = LocalDate.parse(expirationDate).atStartOfDay();
            // Construir el objeto request con los parámetros recibidos
            UpdateDigitalSignatureCertificateRequest request = UpdateDigitalSignatureCertificateRequest.builder()
                    .certificateName(certificateName)
                    .certificateP12Base64(certificateP12Base64)
                    .certificatePassword(certificatePassword)
                    .expirationDate(dateTime)
                    .isActive(isActive)
                    .isPrimaryKey(isPrimaryKey)
                    .businessId(businessId)
                    .build();
            
            UpdateDigitalSignatureCertificateCommand command = UpdateDigitalSignatureCertificateCommand.fromRequest(request, id, userId);
            UpdateDigitalSignatureCertificateMessage response = mediator.send(command);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error al procesar archivo de certificado: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("ERROR-CERT-002", "Error al procesar el archivo de certificado: " + e.getMessage(), false));
        }
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
