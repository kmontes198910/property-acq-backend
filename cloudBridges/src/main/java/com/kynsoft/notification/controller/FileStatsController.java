package com.kynsoft.notification.controller;

import com.kynsof.share.core.domain.response.ApiResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.notification.application.query.file.countbypath.FileCountByPathResponse;
import com.kynsoft.notification.application.query.file.countbypath.GetFileCountByPathQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controlador para estadísticas y análisis de archivos almacenados
 */
@RestController
@RequestMapping("/api/file-stats")
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
    @GetMapping("/count-by-path/{businessId}")
    public ResponseEntity<?> getFileCountByPath(@PathVariable UUID businessId) {
        logger.info("Solicitando conteo de archivos agrupados por path para la empresa: {}", businessId);
        
        // Crear y enviar la consulta
        GetFileCountByPathQuery query = new GetFileCountByPathQuery(businessId);
        FileCountByPathResponse response = mediator.send(query);
        
        // Devolver la respuesta
        logger.info("Se encontraron {} archivos en total para la empresa: {}", response.getTotalCount(), businessId);
        return ResponseEntity.ok(response);
    }
}