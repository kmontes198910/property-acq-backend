package com.kynsoft.report.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.ApiResponse;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.report.applications.command.jasperReportTemplate.create.CreateJasperReportTemplateCommand;
import com.kynsoft.report.applications.command.jasperReportTemplate.create.CreateJasperReportTemplateMessage;
import com.kynsoft.report.applications.command.jasperReportTemplate.delete.DeleteJasperReportTemplateCommand;
import com.kynsoft.report.applications.command.jasperReportTemplate.delete.DeleteJasperReportTemplateMessage;
import com.kynsoft.report.applications.command.jasperReportTemplate.update.UpdateJasperReportTemplateCommand;
import com.kynsoft.report.applications.command.jasperReportTemplate.update.UpdateJasperReportTemplateMessage;
import com.kynsoft.report.applications.command.jasperReportTemplate.update.UpdateJasperReportTemplateRequest;
import com.kynsoft.report.applications.query.jasperreporttemplate.getbyid.FindJasperReportTemplateByIdQuery;
import com.kynsoft.report.applications.query.jasperreporttemplate.getbyid.JasperReportTemplateResponse;
import com.kynsoft.report.applications.query.jasperreporttemplate.search.GetJasperReportTemplateQuery;
import com.kynsoft.report.domain.dto.JasperReportTemplateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/jasper-report-template")
public class JasperReportTemplateController {

    private final IMediator mediator;

    @Autowired
    public JasperReportTemplateController(IMediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping(value = "create")
    public Mono<ResponseEntity<ApiResponse<?>>> upload(
            @RequestPart("file") FilePart filePart,
            @RequestPart("reportCode") String reportCode,
            @RequestPart("name") String name,
            @RequestPart("description") String description,
            @RequestPart("type") String type,
            @RequestPart("dbConection") String dbConection
    ) {
        // Asignar cadena vacía si objectId es null
        // String valueId = (objectId == null) ? "" : objectId;
        return DataBufferUtils.join(filePart.content())
                .flatMap(dataBuffer -> {

                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);

                    // ✅ Obtener el tipo de contenido (MIME type)
                    String contentType = Optional.ofNullable(filePart.headers().getContentType())
                            .map(MediaType::toString)
                            .orElse("application/octet-stream");

                    // ✅ Verificar los bytes obtenidos (opcional para depuración)
                    System.out.println("File received: " + filePart.filename());
                    System.out.println("Size: " + bytes.length + " bytes");
                    System.out.println("MIME Type: " + contentType);


                    CreateJasperReportTemplateCommand createCommand = new CreateJasperReportTemplateCommand(
                            reportCode,
                            name,
                            description,
                            JasperReportTemplateType.REPORT,
                            bytes,
                            UUID.fromString(dbConection)
                    );
                    CreateJasperReportTemplateMessage response = mediator.send(createCommand);
                    try {

                        return Mono.just(ResponseEntity.ok(ApiResponse.success(response)));
                    } catch (Exception e) {
                        return Mono.error(e);
                    }
                });
    }

//    @PostMapping("")
//    public ResponseEntity<?> create(@RequestBody CreateJasperReportTemplateRequest request) {
//        CreateJasperReportTemplateCommand createCommand = CreateJasperReportTemplateCommand.fromRequest(request);
//        CreateJasperReportTemplateMessage response = mediator.send(createCommand);
//
//        return ResponseEntity.ok(response);
//    }

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
