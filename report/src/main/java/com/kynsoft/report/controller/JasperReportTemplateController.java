package com.kynsoft.report.controller;

import com.kynsof.share.core.domain.request.PageableUtil;
import com.kynsof.share.core.domain.request.SearchRequest;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.report.applications.command.jasperReportTemplate.create.CreateJasperReportTemplateCommand;
import com.kynsoft.report.applications.command.jasperReportTemplate.delete.DeleteJasperReportTemplateCommand;
import com.kynsoft.report.applications.command.jasperReportTemplate.delete.DeleteJasperReportTemplateMessage;
import com.kynsoft.report.applications.command.jasperReportTemplate.update.UpdateJasperReportTemplateCommand;
import com.kynsoft.report.applications.command.jasperReportTemplate.update.UpdateJasperReportTemplateMessage;
import com.kynsoft.report.applications.command.jasperReportTemplate.update.UpdateJasperReportTemplateRequest;
import com.kynsoft.report.applications.query.jasperreporttemplate.getbyid.FindJasperReportTemplateByIdQuery;
import com.kynsoft.report.applications.query.jasperreporttemplate.getbyid.JasperReportTemplateResponse;
import com.kynsoft.report.applications.query.jasperreporttemplate.search.GetJasperReportTemplateQuery;
import com.kynsoft.report.domain.dto.JasperReportTemplateType;
import com.kynsoft.report.domain.dto.status.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;

@RestController
@RequestMapping("/api/jasper-report-template")
public class JasperReportTemplateController {

    private final IMediator mediator;

    @Autowired
    public JasperReportTemplateController(IMediator mediator) {
        this.mediator = mediator;
    }


    @PostMapping(value = "create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<ResponseEntity<String>> create(
            @RequestPart("file") Mono<FilePart> filePartMono,
            @RequestParam("code") String code,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("type") JasperReportTemplateType type,
            @RequestParam("parameters") String parameters,
            @RequestParam("dbConection") UUID dbConection
    ) {
        return filePartMono
                .flatMap(filePart -> DataBufferUtils.join(filePart.content())
                        .flatMap(dataBuffer -> {
                            byte[] fileBytes = new byte[dataBuffer.readableByteCount()];
                            dataBuffer.read(fileBytes);
                            DataBufferUtils.release(dataBuffer); // Liberar memoria
                            return Mono.just(fileBytes);
                        })
                )
                .flatMap(fileBytes -> Mono.fromRunnable(() -> {
                            // Crear el comando con los parámetros
                            CreateJasperReportTemplateCommand createCommand = new CreateJasperReportTemplateCommand(
                                    code, name, description, type, fileBytes, parameters, dbConection, "", Status.ACTIVE
                            );

                            // Enviar el comando a través del mediator
                            mediator.send(createCommand);
                        })
                        .subscribeOn(Schedulers.boundedElastic()))
                .thenReturn(ResponseEntity.ok("Archivo procesado y comando creado correctamente"))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error procesando el archivo: " + e.getMessage()))
                );
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
