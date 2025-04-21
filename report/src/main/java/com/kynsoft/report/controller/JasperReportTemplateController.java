package com.kynsoft.report.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.ApiError;
import com.kynsof.share.core.domain.response.ApiResponse;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.report.applications.command.jasperReportTemplate.create.CreateJasperReportTemplateCommand;
import com.kynsoft.report.applications.command.jasperReportTemplate.create.CreateJasperReportTemplateMessage;
import com.kynsoft.report.applications.command.jasperReportTemplate.create.CreateJasperReportTemplateRequest;
import com.kynsoft.report.applications.command.jasperReportTemplate.delete.DeleteJasperReportTemplateCommand;
import com.kynsoft.report.applications.command.jasperReportTemplate.delete.DeleteJasperReportTemplateMessage;
import com.kynsoft.report.applications.command.jasperReportTemplate.update.UpdateJasperReportTemplateCommand;
import com.kynsoft.report.applications.command.jasperReportTemplate.update.UpdateJasperReportTemplateMessage;
import com.kynsoft.report.applications.command.jasperReportTemplate.update.UpdateJasperReportTemplateRequest;
import com.kynsoft.report.applications.query.jasperreporttemplate.getbyid.FindJasperReportTemplateByIdQuery;
import com.kynsoft.report.applications.query.jasperreporttemplate.getbyid.JasperReportTemplateResponse;
import com.kynsoft.report.applications.query.jasperreporttemplate.search.GetJasperReportTemplateQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/jasper-report-template")
public class JasperReportTemplateController {

    private final IMediator mediator;

    @Autowired
    public JasperReportTemplateController(IMediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping(value = "create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<?>> upload(@RequestBody CreateJasperReportTemplateRequest request) {
        try {
            // Validaciones básicas
            if (request.getFileBase64() == null || request.getFileBase64().isEmpty()) {
                return ResponseEntity.badRequest().body(
                    ApiResponse.fail(new ApiError("El archivo en base64 es requerido"))
                );
            }
            
            // Verificar los datos recibidos (para depuración)
            System.out.println("Request received: " + request.getCode());
            System.out.println("Base64 file size: " + request.getFileBase64().length() + " characters");
            
            // Crear el comando a partir de la solicitud
            CreateJasperReportTemplateCommand createCommand = CreateJasperReportTemplateCommand.fromRequest(request);
            
            // Enviar el comando y obtener la respuesta
            CreateJasperReportTemplateMessage response = mediator.send(createCommand);
            
            // Devolver una respuesta exitosa
            return ResponseEntity.ok(ApiResponse.success(response));
            
        } catch (IllegalArgumentException e) {
            // Manejar errores de formato
            System.err.println("Error de formato: " + e.getMessage());
            return ResponseEntity.badRequest().body(
                ApiResponse.fail(new ApiError("Error en el formato de los datos: " + e.getMessage()))
            );
        } catch (Exception e) {
            // Manejar otros errores
            System.err.println("Error al crear la plantilla: " + e.getMessage());
            return ResponseEntity.badRequest().body(
                ApiResponse.fail(new ApiError("Error al crear la plantilla: " + e.getMessage()))
            );
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {

        FindJasperReportTemplateByIdQuery query = new FindJasperReportTemplateByIdQuery(id);
        JasperReportTemplateResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<PaginatedResponse> search(@RequestBody SearchRequest request) {
        Pageable pageable = PageableUtil.createPageable(request);
        GetJasperReportTemplateQuery query = new GetJasperReportTemplateQuery(pageable,
                request.getFilter(), request.getQuery());
        PaginatedResponse data = mediator.send(query);
        return ResponseEntity.ok(data);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UpdateJasperReportTemplateMessage> update(@PathVariable("id") UUID id,
                                                                    @RequestBody UpdateJasperReportTemplateRequest request) {

        UpdateJasperReportTemplateCommand command = UpdateJasperReportTemplateCommand.fromRequest(request, id);
        UpdateJasperReportTemplateMessage response = mediator.send(command);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {

        DeleteJasperReportTemplateCommand query = new DeleteJasperReportTemplateCommand(id);
        DeleteJasperReportTemplateMessage response = mediator.send(query);
        return ResponseEntity.ok(response);
    }

}
