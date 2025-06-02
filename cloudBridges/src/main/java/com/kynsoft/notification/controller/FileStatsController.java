package com.kynsoft.notification.controller;

import com.kynsof.share.core.domain.response.ApiError;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.notification.application.query.file.countbymimetype.FileCountByMimeTypeResponse;
import com.kynsoft.notification.application.query.file.countbymimetype.GetFileCountByMimeTypeQuery;
import com.kynsoft.notification.application.query.file.countbypath.FileCountByPathResponse;
import com.kynsoft.notification.application.query.file.countbypath.GetFileCountByPathQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Controlador para estadísticas y análisis de archivos almacenados
 */
@RestController
@RequestMapping("/api/file-stats")
@Tag(name = "Estadísticas de Archivos", description = "Endpoints para obtener estadísticas de archivos")
public class FileStatsController {

    private static final Logger logger = LoggerFactory.getLogger(FileStatsController.class);
    private final IMediator mediator;

    public FileStatsController(IMediator mediator) {
        this.mediator = mediator;
    }

    /**
     * Endpoint para obtener el conteo de archivos agrupados por path para una empresa específica
     * @param businessId ID de la empresa
     * @return Respuesta con los conteos de archivos agrupados por path
     */
    @Operation(summary = "Obtener conteo de archivos por path", description = "Devuelve la cantidad de archivos y el tamaño agrupados por path (ruta) para una empresa específica")
    @GetMapping("/count-by-path/{businessId}")
    public ResponseEntity<?> getFileCountByPath(
            @Parameter(description = "ID de la empresa", required = true)
            @PathVariable UUID businessId) {
        logger.info("Solicitando conteo de archivos agrupados por path para la empresa: {}", businessId);
        
        // Crear y enviar la consulta
        GetFileCountByPathQuery query = new GetFileCountByPathQuery(businessId);
        FileCountByPathResponse response = mediator.send(query);
        
        // Devolver la respuesta
        logger.info("Se encontraron {} archivos en total para la empresa: {}", response.getTotalCount(), businessId);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para obtener estadísticas de archivos por tipo MIME para una empresa específica.
     * Devuelve la cantidad de archivos y el espacio ocupado agrupados por tipo MIME.
     * 
     * @param businessId ID de la empresa
     * @return Respuesta con estadísticas detalladas por tipo MIME
     */
    @Operation(summary = "Obtener estadísticas por tipo MIME", description = "Devuelve estadísticas detalladas de archivos agrupadas por tipo MIME para una empresa específica")

    @GetMapping("/count-by-mimetype/{businessId}")
    public ResponseEntity<?> getFileCountByMimeType(
            @Parameter(description = "ID de la empresa", required = true)
            @PathVariable UUID businessId) {
        try {
            logger.info("Solicitando estadísticas de archivos por mimeType para empresa: {}", businessId);

            GetFileCountByMimeTypeQuery query = new GetFileCountByMimeTypeQuery(businessId);
            FileCountByMimeTypeResponse response = mediator.send(query);

            logger.info("Estadísticas obtenidas: {} tipos MIME, {} archivos en total, {} GB",
                    response.getMimeTypeStats().size(),
                    response.getTotalFileCount(),
                    response.getTotalSizeGB());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error al obtener estadísticas por mimeType: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(
                    com.kynsof.share.core.domain.response.ApiResponse.fail(new ApiError(500, "Error al obtener estadísticas: " + e.getMessage()))
            );
        }
    }
}