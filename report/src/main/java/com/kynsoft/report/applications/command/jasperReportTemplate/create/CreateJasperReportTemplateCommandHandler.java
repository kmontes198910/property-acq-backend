package com.kynsoft.report.applications.command.jasperReportTemplate.create;


import com.kynsof.share.core.application.FileServices.IFileUploadService;
import com.kynsof.share.core.application.FileServices.UploadResponse;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.report.domain.dto.DBConnectionDto;
import com.kynsoft.report.domain.dto.JasperReportTemplateDto;
import com.kynsoft.report.domain.dto.status.Status;
import com.kynsoft.report.domain.services.IDBConnectionService;
import com.kynsoft.report.domain.services.IJasperReportTemplateService;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.UUID;

@Component
public class CreateJasperReportTemplateCommandHandler implements ICommandHandler<CreateJasperReportTemplateCommand> {

    private final IJasperReportTemplateService service;
    private final IDBConnectionService connectionService;
    private final IFileUploadService fileUploadService;

    public CreateJasperReportTemplateCommandHandler(IJasperReportTemplateService service, IDBConnectionService conectionService, IFileUploadService fileUploadService) {
        this.service = service;
        this.connectionService = conectionService;
        this.fileUploadService = fileUploadService;
    }

    @Override
    public void handle(CreateJasperReportTemplateCommand command) {
        DBConnectionDto dbConnectionDto = command.getDbConection() != null ? this.connectionService.findById(command.getDbConection()) : null;
        System.err.println("Create Report Template");
        try {
            // 1️⃣ Obtener el contenido del archivo en bytes
            byte[] jrxmlBytes = command.getFile();
            if (jrxmlBytes == null || jrxmlBytes.length == 0) {
                throw new RuntimeException("El archivo JRXML está vacío o no se proporcionó.");
            }

            System.setProperty("net.sf.jasperreports.compiler.useThreadContextClassLoader", "true");
            System.setProperty("net.sf.jasperreports.compiler.class", "net.sf.jasperreports.engine.design.JRJdtCompiler");

            InputStream jrxmlInputStream = new ByteArrayInputStream(jrxmlBytes);
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlInputStream);

            // 4️⃣ Convertir `JasperReport` a bytes
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(jasperReport);
            objectOutputStream.flush();
            byte[] jasperBytes = outputStream.toByteArray();
            objectOutputStream.close();
            outputStream.close();

            // ✅ Log para depuración
            System.out.println("Archivo Jasper compilado correctamente.");

            // 5️⃣ Llamar al endpoint `/upload` para subir el archivo compilado a S3
            //  uploadJasperToS3(jasperBytes, command.getCode());


            // 5️⃣ Guardar la referencia en base de datos (si aplica)
            // UploadResponse result=  uploadJasperToS3(jasperBytes, command.getCode());
            UploadResponse result = fileUploadService.uploadJasperToS3(jasperBytes, command.getCode(),
                    "jasper-reports/");

            System.err.println("Url del Jasper: " + result.getUrl());
            JasperReportTemplateDto templateDto = new JasperReportTemplateDto(
                    UUID.randomUUID(),
                    command.getCode(),
                    command.getName(),
                    command.getDescription(),
                    result.getUrl(),
                    command.getType(),
                    dbConnectionDto,
                    Status.ACTIVE
            );

            service.create(templateDto);

            System.err.println("OK");
        } catch (Exception e) {
            System.err.println(e.getMessage());

        }

    }
//
//    private UploadResponse uploadJasperToS3(byte[] jasperBytes, String fileName) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        // Crear el cuerpo de la solicitud multipart
//        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//        body.add("fileService", new ByteArrayResource(jasperBytes) {
//            @Override
//            public String getFilename() {
//                return fileName + ".jasper";
//            }
//        });
//        body.add("folderPath", "jasper-reports/");
//        body.add("objectId", fileName);
//
//        // Crear la cabecera con tipo de contenido multipart/form-data
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//
//        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
//
//        // URL del servicio donde subimos el archivo
//        String uploadUrl = "http://localhost:8097/api/files/upload";
//
//        // Enviar la petición y obtener la respuesta bloqueante
//        ResponseEntity<Map> response = restTemplate.exchange(
//                uploadUrl, HttpMethod.POST, requestEntity, Map.class
//        );
//
//        // Extraer solo `data` y mapear a `UploadResponse`
//        if (response.getBody() != null) {
//            Map<String, Object> bodyResponse = (Map<String, Object>) response.getBody().get("body");
//            if (bodyResponse != null && bodyResponse.containsKey("data")) {
//                Map<String, Object> data = (Map<String, Object>) bodyResponse.get("data");
//                return new UploadResponse((String) data.get("url"), (String) data.get("id")); // ✅ Mapea a `UploadResponse`
//            }
//        }
//
//        throw new RuntimeException("❌ No se pudo extraer `url` e `id` de la respuesta");
//    }
}
