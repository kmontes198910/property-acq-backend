package com.kynsoft.notification.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.ApiError;
import com.kynsof.share.core.domain.response.ApiResponse;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.notification.application.command.file.deleteFileS3.DeleteFileS3Command;
import com.kynsoft.notification.application.command.file.deleteFileS3.DeleteFileS3Message;
import com.kynsoft.notification.application.command.file.saveFileS3.SaveFileS3Command;
import com.kynsoft.notification.application.command.file.saveFileS3.SaveFileS3Message;
import com.kynsoft.notification.application.query.file.search.GetSearchAFileQuery;
import com.kynsoft.notification.domain.dto.AFileDto;
import com.kynsoft.notification.domain.dto.FileBase64UploadDto;
import com.kynsoft.notification.domain.dto.FileInfoDto;
import com.kynsoft.notification.domain.service.IAFileService;
import com.kynsoft.notification.infrastructure.service.AmazonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/files")
public class FileController {
    
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    private final IMediator mediator;

    @Autowired
    public FileController(IMediator mediator) {
        this.mediator = mediator;
    }

    /**
     * Carga un archivo en S3
     */
    @PostMapping(value = "")
    public ResponseEntity<ApiResponse<?>> upload(
            @RequestPart("file") MultipartFile file,
            @RequestPart("objectId") String objectId) {
        
        logger.info("Recibida solicitud para cargar archivo: {}, objectId: {}", 
                file.getOriginalFilename(), objectId);
        
        return processFileUpload(file, objectId, "");
    }

    /**
     * Carga un archivo en S3 con posibilidad de especificar una ruta de carpeta
     * @param file Archivo a cargar
     * @param objectId ID del objeto asociado (opcional)
     * @param folderPath Ruta de la carpeta donde se guardará el archivo
     * @return Respuesta con la información del archivo cargado
     */
    @PostMapping(value = "upload")
    public ResponseEntity<ApiResponse<?>> uploadFile(
            @RequestPart("file") MultipartFile file,
            @RequestPart(value = "objectId", required = false) String objectId,
            @RequestPart(value = "folderPath") String folderPath,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Name", required = false) String userName) {
        
        logger.info("Recibida solicitud para cargar archivo con folderPath: {}, objectId: {}", 
                folderPath, objectId);
        
        return processFileUpload(file, objectId, folderPath, userId, userName);
    }

    /**
     * Método común para procesar la carga del archivo
     */
    private ResponseEntity<ApiResponse<?>> processFileUpload(
            MultipartFile file,
            String objectId,
            String folderPath) {
        return processFileUpload(file, objectId, folderPath, "", "");
    }

    /**
     * Método común para procesar la carga del archivo con información de usuario
     */
    private ResponseEntity<ApiResponse<?>> processFileUpload(
            MultipartFile file,
            String objectId,
            String folderPath,
            String userId,
            String userName) {

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
                    userName
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


