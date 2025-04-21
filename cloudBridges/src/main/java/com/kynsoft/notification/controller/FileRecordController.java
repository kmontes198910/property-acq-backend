package com.kynsoft.notification.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.ApiError;
import com.kynsof.share.core.domain.response.ApiResponse;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.notification.application.command.file.saveFileS3.SaveFileS3Command;
import com.kynsoft.notification.application.command.file.saveFileS3.SaveFileS3Message;
import com.kynsoft.notification.application.query.file.delete.DeleteFileCommand;
import com.kynsoft.notification.application.query.file.delete.DeleteFileMessage;
import com.kynsoft.notification.application.query.file.getbyid.FindByIdAFileQuery;
import com.kynsoft.notification.application.query.file.search.FileResponse;
import com.kynsoft.notification.application.query.file.search.GetSearchAFileQuery;
import com.kynsoft.notification.domain.dto.FileBase64UploadDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/file-record")
public class FileRecordController {
    private static final Logger logger = LoggerFactory.getLogger(FileRecordController.class);
    private final IMediator mediator;

    public FileRecordController(IMediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request) {
        Pageable pageable = PageableUtil.createPageable(request);
        GetSearchAFileQuery query = new GetSearchAFileQuery(pageable, request.getFilter(), request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<FileResponse> getById(@PathVariable UUID id) {

        FindByIdAFileQuery query = new FindByIdAFileQuery(id);
        FileResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteFileCommand query = new DeleteFileCommand(id);
        DeleteFileMessage response = mediator.send(query);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para cargar archivos en formato base64 a través del cuerpo JSON
     * @param fileUploadDto DTO con el contenido base64 del archivo y metadatos
     * @param userId ID del usuario que realiza la carga (opcional)
     * @param userName Nombre del usuario que realiza la carga (opcional)
     * @return Respuesta con la información del archivo cargado
     */
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<?>> uploadBase64File(
            @RequestBody FileBase64UploadDto fileUploadDto,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Name", required = false) String userName) {

        logger.info("Recibida solicitud para cargar archivo base64 con nombre: {}, folderPath: {}, objectId: {}",
                fileUploadDto.getFileName(), fileUploadDto.getFolderPath(), fileUploadDto.getObjectId());

        try {
            // Decodificar el contenido base64
            byte[] fileContent = Base64.getDecoder().decode(fileUploadDto.getBase64Content());

            // Crear un MultipartFile a partir del contenido base64
            String fileName = Optional.ofNullable(fileUploadDto.getFileName())
                    .filter(name -> !name.isEmpty())
                    .orElse("file_" + System.currentTimeMillis());

            // Determinar el tipo de contenido basado en la extensión del archivo o usar uno genérico
            String contentType = determineContentType(fileName);

            // Crear un archivo multipart simulado
            MultipartFile multipartFile = new MockMultipartFile(
                    fileName,
                    fileName,
                    contentType,
                    fileContent
            );

            // Procesar la carga del archivo
            return processFileUpload(
                    multipartFile,
                    fileUploadDto.getObjectId(),
                    fileUploadDto.getFolderPath(),
                    userId,
                    userName,
                    fileUploadDto.getBusinessId()
            );

        } catch (IllegalArgumentException e) {
            logger.error("Error al decodificar contenido base64: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(
                    ApiResponse.fail(new ApiError(400, "Contenido base64 inválido: " + e.getMessage()))
            );
        } catch (Exception e) {
            logger.error("Error al procesar archivo base64: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(
                    ApiResponse.fail(new ApiError(500, "Error al procesar archivo: " + e.getMessage()))
            );
        }
    }
    /**
     * Determina el tipo de contenido (MIME type) basado en la extensión del archivo
     * @param fileName Nombre del archivo
     * @return Tipo de contenido MIME
     */
    private String determineContentType(String fileName) {
        String lowerCaseFileName = fileName.toLowerCase();
        if (lowerCaseFileName.endsWith(".pdf")) {
            return "application/pdf";
        } else if (lowerCaseFileName.endsWith(".jpg") || lowerCaseFileName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (lowerCaseFileName.endsWith(".png")) {
            return "image/png";
        } else if (lowerCaseFileName.endsWith(".gif")) {
            return "image/gif";
        } else if (lowerCaseFileName.endsWith(".doc")) {
            return "application/msword";
        } else if (lowerCaseFileName.endsWith(".docx")) {
            return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        } else if (lowerCaseFileName.endsWith(".xls")) {
            return "application/vnd.ms-excel";
        } else if (lowerCaseFileName.endsWith(".xlsx")) {
            return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        } else if (lowerCaseFileName.endsWith(".txt")) {
            return "text/plain";
        } else if (lowerCaseFileName.endsWith(".csv")) {
            return "text/csv";
        } else if (lowerCaseFileName.endsWith(".json")) {
            return "application/json";
        } else if (lowerCaseFileName.endsWith(".xml")) {
            return "application/xml";
        }

        // Tipo genérico para otros formatos
        return "application/octet-stream";
    }
    /**
     * Método común para procesar la carga del archivo con información de usuario
     */
    private ResponseEntity<ApiResponse<?>> processFileUpload(
            MultipartFile file,
            String objectId,
            String folderPath,
            String userId,
            String userName,
            UUID businessId) {

        String valueId = Optional.ofNullable(objectId).orElse("");
        String folder = Optional.ofNullable(folderPath).filter(f -> !f.isEmpty()).orElse("");

        try {
            // Validación del archivo
            if (file.isEmpty()) {
                throw new IllegalArgumentException("El archivo está vacío");
            }

            // Información del usuario
            userId = Optional.ofNullable(userId).orElse("");
            userName = Optional.ofNullable(userName).orElse("");

            logger.info("Procesando subida de archivo por usuario: {} (ID: {})", userName, userId);

            // Extraer información del archivo
            String contentType = file.getContentType();
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            String filename = file.getOriginalFilename();
            if (filename == null) {
                filename = "file_" + System.currentTimeMillis();
            }

            // Crear comando
            SaveFileS3Command command = new SaveFileS3Command(
                    file,
                    filename,
                    valueId,
                    folder,
                    userId,
                    userName,
                    businessId
            );

            // Ejecutar comando
            SaveFileS3Message response = mediator.send(command);
            logger.info("Archivo cargado exitosamente por {} (ID: {}): {}",
                    userName, userId, response.getId());

            return ResponseEntity.ok(ApiResponse.success(response));

        } catch (Exception e) {
            logger.error("Error al procesar el archivo {}: {}",
                    file.getOriginalFilename(), e.getMessage(), e);
            return ResponseEntity.badRequest().body(
                    ApiResponse.fail(new ApiError(400, e.getMessage()))
            );
        }
    }

}
