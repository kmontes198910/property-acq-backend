package com.kynsoft.report.controller;

import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.report.applications.command.generateReport.GenerateReportCommand;
import com.kynsoft.report.applications.command.generateReport.GenerateReportMessage;
import com.kynsoft.report.applications.command.generateReport.GenerateReportRequest;
import com.kynsoft.report.applications.command.generateTemplate1.GenerateTemplateCommand;
import com.kynsoft.report.applications.command.generateTemplate1.GenerateTemplateMessage;
import com.kynsoft.report.applications.command.generateTemplate1.GenerateTemplateRequest;
import com.kynsoft.report.applications.query.reportTemplate.GetReportParameterByCodeQuery;
import com.kynsoft.report.applications.query.reportTemplate.GetReportParameterByCodeResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final IMediator mediator;

    public ReportController(IMediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping(value = "/generate-template", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<?> getTemplate(@RequestBody GenerateTemplateRequest request) {

        GenerateTemplateCommand command = new GenerateTemplateCommand(request.getParameters(),
                request.getJasperReportCode());
        GenerateTemplateMessage response = mediator.send(command);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(response.getResult());
    }

    @PostMapping(value = "/generate", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Map<String, String>>> generateReport(@RequestBody GenerateReportRequest request) {
        return Mono.fromCallable(() -> {
            GenerateReportCommand command = new GenerateReportCommand(request.getParameters(),
                    request.getJasperReportCode(), request.getReportFormatType());
            GenerateReportMessage response = mediator.send(command);

            // Convertir el resultado (PDF) a Base64
            String base64Report = Base64.getEncoder().encodeToString(response.getResult());

            // Construir la respuesta como un JSON
            Map<String, String> jsonResponse = new HashMap<>();
            jsonResponse.put("base64Report", base64Report);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(jsonResponse);
        });
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

}

