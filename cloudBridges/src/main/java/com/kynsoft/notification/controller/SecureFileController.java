package com.kynsoft.notification.controller;

import com.kynsoft.notification.config.CacheableFileData;
import com.kynsoft.notification.domain.dto.AFileDto;
import com.kynsoft.notification.domain.service.IAFileService;
import com.kynsoft.notification.infrastructure.config.CloudBridgesCacheConfig;
import com.kynsoft.notification.infrastructure.service.AmazonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/secure/files")
public class SecureFileController {

    private static final Logger logger = LoggerFactory.getLogger(SecureFileController.class);
    private final IAFileService fileService;
    private final AmazonClient amazonClient;

    @Autowired
    public SecureFileController(IAFileService fileService, AmazonClient amazonClient) {
        this.fileService = fileService;
        this.amazonClient = amazonClient;
    }


    /**
     * Método que obtiene y cachea los datos del archivo
     */
    @Cacheable(value = CloudBridgesCacheConfig.SECURE_FILE_CACHE, key = "#id", unless = "#result == null")
    public CacheableFileData getFileData(UUID id) {
        try {
            logger.info("Obteniendo datos para archivo con ID: {}", id);
            
            // 1. Buscar el archivo en la base de datos
            AFileDto fileRecord = fileService.findById(id);
            if (fileRecord == null) {
                logger.warn("Archivo con ID {} no encontrado en la base de datos", id);
                return null;
            }
            
            // 2. Obtener la URL del archivo
            String fileUrl = fileRecord.getUrl();
            logger.info("URL del archivo encontrada: {}", fileUrl);
            
            // 3. Cargar el archivo desde S3
            AFileDto fileContent = amazonClient.loadFile(fileUrl);
            if (fileContent == null || fileContent.getFileContent() == null) {
                logger.error("No se pudo cargar el archivo desde S3: {}", fileUrl);
                return null;
            }
            
            // 4. Asegurarse de que tenemos un tipo MIME válido
            String mimeType = fileContent.getMimeType();
            if (mimeType == null || mimeType.isEmpty()) {
                mimeType = determineContentType(fileContent.getName());
                logger.info("Tipo MIME inferido: {}", mimeType);
            }
            
            // Forzar el tipo MIME para PDF si el nombre tiene extensión .pdf
            if (fileContent.getName() != null && fileContent.getName().toLowerCase().endsWith(".pdf")) {
                mimeType = "application/pdf";
                logger.info("Aplicando tipo MIME forzado para PDF");
            }
            
            // 5. Decodificar el contenido
            byte[] fileBytes = Base64.getDecoder().decode(fileContent.getFileContent());
            logger.info("Tamaño del archivo decodificado: {} bytes", fileBytes.length);
            
            return new CacheableFileData(fileBytes, fileContent.getName(), mimeType);
        } catch (Exception e) {
            logger.error("Error al procesar la solicitud de datos: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * Endpoint para visualizar archivos desde la base de datos
     * Usa el método cacheado para obtener los datos del archivo
     */
    @GetMapping("/view/{id}")
    public ResponseEntity<Resource> viewSecureFile(
            @PathVariable UUID id,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Name", required = false) String userName) {

        try {
            logger.info("Solicitud de visualización para archivo con ID: {}", id);
            
            // Obtener datos cacheados
            CacheableFileData fileData = getFileData(id);
            if (fileData == null) {
                return ResponseEntity.notFound().build();
            }
            
            // Preparar las cabeceras HTTP
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(fileData.getMimeType()));
            
            // Para visualización en el navegador
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileData.getFileName() + "\"");
            
            // Crear el recurso con el contenido del archivo
            ByteArrayResource resource = new ByteArrayResource(fileData.getFileBytes());
            
            // Devolver la respuesta
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(fileData.getFileBytes().length)
                    .body(resource);
            
        } catch (Exception e) {
            logger.error("Error al procesar la solicitud: {}", e.getMessage(), e);
            return ResponseEntity.status(500).build();
        }
    }
    
    /**
     * Endpoint para descargar archivos desde la base de datos
     */
    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadSecureFile(
            @PathVariable UUID id,
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Name", required = false) String userName) {

        try {
            logger.info("Solicitud de descarga para archivo con ID: {}", id);
            
            // 1. Buscar el archivo en la base de datos
            AFileDto fileRecord = fileService.findById(id);
            if (fileRecord == null) {
                logger.warn("Archivo con ID {} no encontrado en la base de datos", id);
                return ResponseEntity.notFound().build();
            }
            
            // 2. Obtener la URL del archivo
            String fileUrl = fileRecord.getUrl();
            logger.info("URL del archivo encontrada: {}", fileUrl);
            
            // 3. Cargar el archivo desde S3
            AFileDto fileContent = amazonClient.loadFile(fileUrl);
            if (fileContent == null) {
                logger.error("No se pudo cargar el archivo desde S3: {}", fileUrl);
                return ResponseEntity.notFound().build();
            }
            
            if (fileContent.getFileContent() == null) {
                logger.error("El contenido del archivo es nulo");
                return ResponseEntity.notFound().build();
            }
            
            // 4. Preparar las cabeceras HTTP
            HttpHeaders headers = new HttpHeaders();
            
            // Asegurarse de que tenemos un tipo MIME válido
            String mimeType = Optional.ofNullable(fileContent.getMimeType())
                    .filter(type -> !type.isEmpty())
                    .orElseGet(() -> determineContentType(fileContent.getName()));
            
            headers.setContentType(MediaType.parseMediaType(mimeType));
            
            // Para descarga
            headers.setContentDispositionFormData("attachment", fileContent.getName());
            
            // 5. Crear el recurso con el contenido del archivo
            byte[] fileBytes = Base64.getDecoder().decode(fileContent.getFileContent());
            ByteArrayResource resource = new ByteArrayResource(fileBytes);
            
            logger.info("Archivo descargado exitosamente: {} ({})", fileContent.getName(), mimeType);
            
            // 6. Devolver la respuesta
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(fileBytes.length)
                    .body(resource);
            
        } catch (Exception e) {
            logger.error("Error al procesar la solicitud: {}", e.getMessage(), e);
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Determina el tipo de contenido (MIME type) basado en la extensión del archivo
     */
    private String determineContentType(String fileName) {
        if (fileName == null) {
            return "application/octet-stream";
        }
        
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
}