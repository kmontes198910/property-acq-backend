package com.kynsoft.notification.controller;

import com.kynsof.share.core.domain.response.ApiError;
import com.kynsof.share.core.domain.response.ApiResponse;
import com.kynsoft.notification.domain.dto.FileInfoDto;
import com.kynsoft.notification.infrastructure.service.AmazonClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Controlador para gestionar la eliminación de archivos en Amazon S3
 */
@RestController
@RequestMapping("/api/s3-files")
@Tag(name = "Administración de Archivos S3", description = "Endpoints para gestionar archivos en Amazon S3")
public class DeleteFileS3Controller {

    private static final Logger logger = LoggerFactory.getLogger(DeleteFileS3Controller.class);
    private final AmazonClient amazonClient;
    
    @Value("${aws.bucketName}")
    private String bucketName;

    public DeleteFileS3Controller(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    /**
     * Endpoint para listar todos los archivos en el bucket de S3
     * @return Lista de archivos en el bucket
     */
    @Operation(summary = "Listar archivos S3", description = "Lista todos los archivos almacenados en el bucket S3")
    @GetMapping("/list")
    public ResponseEntity<?> listS3Files() {
        try {
            logger.info("Solicitando listado de archivos del bucket: {}", bucketName);
            
            List<FileInfoDto> files = amazonClient.listAllFiles(bucketName);
            
            logger.info("Se encontraron {} archivos en el bucket", files.size());
            return ResponseEntity.ok(ApiResponse.success(files));
        } catch (Exception e) {
            logger.error("Error al listar archivos de S3: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(
                    ApiResponse.fail(new ApiError(500, "Error al listar archivos: " + e.getMessage()))
            );
        }
    }

    /**
     * Endpoint para eliminar todos los archivos del bucket S3
     * @return Resultado de la operación con conteo de archivos eliminados
     */
    @Operation(summary = "Eliminar todos los archivos S3", description = "Elimina todos los archivos almacenados en el bucket S3")
    @DeleteMapping("/delete-all")
    public ResponseEntity<?> deleteAllS3Files() {
        try {
            logger.info("Iniciando proceso de eliminación de todos los archivos del bucket: {}", bucketName);
            
            // Obtener la lista de archivos
            List<FileInfoDto> files = amazonClient.listAllFiles("medinec");
            int totalFiles = files.size();
            
            if (totalFiles == 0) {
                logger.info("No se encontraron archivos para eliminar en el bucket");
                return ResponseEntity.ok(ApiResponse.success("No hay archivos para eliminar"));
            }
            
            // Contador de archivos eliminados exitosamente
            AtomicInteger deletedCount = new AtomicInteger(0);
            
            // Eliminar cada archivo
            for (FileInfoDto file : files) {
                try {
                    logger.info("Eliminando archivo: {}", file.getUrl());
                    amazonClient.delete(file.getUrl());
                    deletedCount.incrementAndGet();
                    logger.info("Archivo eliminado exitosamente: {}", file.getUrl());
                } catch (Exception e) {
                    logger.error("Error al eliminar archivo {}: {}", file.getUrl(), e.getMessage(), e);
                }
            }
            
            // Preparar respuesta con resumen
            String resultMessage = String.format("Proceso completado. Eliminados %d de %d archivos", 
                    deletedCount.get(), totalFiles);
            
            logger.info(resultMessage);
            return ResponseEntity.ok(ApiResponse.success(resultMessage));
        } catch (Exception e) {
            logger.error("Error en el proceso de eliminación de archivos: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(
                    ApiResponse.fail(new ApiError(500, "Error al eliminar archivos: " + e.getMessage()))
            );
        }
    }
    
    /**
     * Endpoint para eliminar un archivo específico por su URL
     * @param url URL del archivo a eliminar
     * @return Resultado de la operación
     */
    @Operation(summary = "Eliminar archivo S3 por URL", description = "Elimina un archivo específico del bucket S3")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteS3File(@RequestParam String url) {
        try {
            logger.info("Solicitando eliminación del archivo: {}", url);
            
            // Verificar si la URL está vacía
            if (url == null || url.isEmpty()) {
                return ResponseEntity.badRequest().body(
                        ApiResponse.fail(new ApiError(400, "La URL del archivo es requerida"))
                );
            }
            
            // Eliminar el archivo
            amazonClient.delete(url);
            
            logger.info("Archivo eliminado exitosamente: {}", url);
            return ResponseEntity.ok(ApiResponse.success("Archivo eliminado exitosamente"));
        } catch (Exception e) {
            logger.error("Error al eliminar archivo: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(
                    ApiResponse.fail(new ApiError(500, "Error al eliminar archivo: " + e.getMessage()))
            );
        }
    }
}