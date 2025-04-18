package com.kynsoft.report.applications.command.jasperReportTemplate.create;


import com.kynsof.share.core.application.FileServices.IFileUploadService;
import com.kynsof.share.core.application.FileServices.UploadResponse;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.report.domain.dto.DBConnectionDto;
import com.kynsoft.report.domain.dto.JasperReportTemplateDto;
import com.kynsoft.report.domain.dto.status.Status;
import com.kynsoft.report.domain.services.IDBConnectionService;
import com.kynsoft.report.domain.services.IJasperReportTemplateService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRSaver;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
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

            // Configuración para el compilador de Jasper
            System.setProperty("net.sf.jasperreports.compiler.class", "net.sf.jasperreports.engine.design.JRJdtCompiler");
            System.setProperty("net.sf.jasperreports.compiler.useThreadContextClassLoader", "true");
            
            // Establecer el class loader adecuado
            Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
            
            // Compilar el reporte desde un InputStream directamente a un objeto JasperReport
            InputStream jrxmlInputStream = new ByteArrayInputStream(jrxmlBytes);
            JasperReport jasperReport;
            try {
                jasperReport = JasperCompileManager.compileReport(jrxmlInputStream);
                System.err.println("Reporte compilado correctamente en memoria");
            } catch (JRException e) {
                e.printStackTrace();
                throw new RuntimeException("Error al compilar el reporte: " + e.getMessage());
            }

            // Convertir el JasperReport a bytes para almacenamiento o transferencia
            ByteArrayOutputStream jasperOutputStream = new ByteArrayOutputStream();
            JRSaver.saveObject(jasperReport, jasperOutputStream);
            byte[] jasperBytes = jasperOutputStream.toByteArray();

            // Imprimir información de depuración
            System.out.println("Archivo Jasper compilado correctamente. Tamaño: " + jasperBytes.length + " bytes");

            // Guardar el reporte en la base de datos sin subir a S3 por ahora
            // En un futuro, descomentar esta línea si se implementa la subida a S3:
           UploadResponse result = fileUploadService.uploadJasperToS3(jasperBytes, command.getCode(), "jasper-reports/");
            
            JasperReportTemplateDto templateDto = new JasperReportTemplateDto(
                    UUID.randomUUID(),
                    command.getCode(),
                    command.getName(),
                    command.getDescription(),
                    "local://jasper-reports/" + command.getCode() + ".jasper", // Usar una URL local por ahora
                    command.getType(),
                    dbConnectionDto,
                    Status.ACTIVE
            );

            service.create(templateDto);

            System.err.println("Template guardado con éxito");
        } catch (Exception e) {
            System.err.println("Error al crear plantilla: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
