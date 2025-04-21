package com.kynsoft.report.controller;

import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.report.applications.command.generateReport.GenerateReportCommand;
import com.kynsoft.report.applications.command.generateReport.GenerateReportMessage;
import com.kynsoft.report.applications.command.generateReport.GenerateReportRequest;
import com.kynsoft.report.applications.query.reportTemplate.GetReportParameterByCodeQuery;
import com.kynsoft.report.applications.query.reportTemplate.GetReportParameterByCodeResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import java.util.concurrent.CompletableFuture;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.TimeUnit;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final IMediator mediator;
    
    @Autowired
    private ThreadPoolTaskExecutor reportTaskExecutor;

    public ReportController(IMediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping(value = "/generate")
    public Mono<ResponseEntity<byte[]>> generateReport(@RequestBody GenerateReportRequest request) {
        String cacheKey = generateCacheKey(request);
        
        // Si forceRefresh es true, evitamos usar la caché
        if (request.isForceRefresh()) {
            return Mono.fromCallable(() -> generateReportDirect(request))
                    .subscribeOn(Schedulers.boundedElastic());
        }
        
        return Mono.fromCallable(() -> generateReportInternal(request, cacheKey))
                .subscribeOn(Schedulers.boundedElastic())
                .cache();
    }
    
    // Método para generar directamente sin usar caché
    private ResponseEntity<byte[]> generateReportDirect(GenerateReportRequest request) {
        GenerateReportCommand command = new GenerateReportCommand(request.getParameters(),
                request.getJasperReportCode(), request.getReportFormatType());
        GenerateReportMessage response = mediator.send(command);

        MediaType mediaType = determineMediaType(request.getReportFormatType());
        String filename = request.getJasperReportCode() + determineFileExtension(request.getReportFormatType());

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header("Content-Disposition", "attachment; filename=\"" + filename + "\"")
                .body(response.getResult());
    }
    
    @Cacheable(value = "reportCache", key = "#cacheKey", 
            condition = "#request.enableCache == true && !#request.forceRefresh", 
            unless = "#result == null")
    private ResponseEntity<byte[]> generateReportInternal(GenerateReportRequest request, String cacheKey) {
        return generateReportDirect(request);
    }
    
    private String generateCacheKey(GenerateReportRequest request) {
        // Genera una clave única para este reporte basada en sus parámetros
        StringBuilder keyBuilder = new StringBuilder(request.getJasperReportCode())
                .append("_").append(request.getReportFormatType());
                
        if (request.getParameters() != null) {
            request.getParameters().forEach((key, value) -> {
                if (value != null) {
                    keyBuilder.append("_").append(key).append("=").append(value.toString());
                }
            });
        }
        
        return keyBuilder.toString();
    }

    @GetMapping("/parameters/{reportCode}")
    public ResponseEntity<?> getReportParameters(@PathVariable String reportCode) {
        GetReportParameterByCodeQuery query = new GetReportParameterByCodeQuery(reportCode);
        GetReportParameterByCodeResponse response = mediator.send(query);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/generate_jasper")
    public ResponseEntity<?> generatedJasper() {
        String jrxmlPath = "/Users/keimermontes/Downloads/actuales/resumen_hc.jrxml";
        String jasperPath = "/Users/keimermontes/Downloads/actuales/resumen_hc.jasper";
        try {
            JasperCompileManager.compileReportToFile(jrxmlPath, jasperPath);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("ok");
    }
    
    private MediaType determineMediaType(String reportFormatType) {
        return "XLS".equalsIgnoreCase(reportFormatType) 
                ? MediaType.parseMediaType("application/vnd.ms-excel") 
                : MediaType.APPLICATION_PDF;
    }
    
    private String determineFileExtension(String reportFormatType) {
        return "XLS".equalsIgnoreCase(reportFormatType) ? ".xlsx" : ".pdf";
    }

}

