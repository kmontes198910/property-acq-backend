package com.kynsoft.report.applications.command.jasperReportTemplate.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.report.domain.dto.DBConectionDto;
import com.kynsoft.report.domain.dto.JasperReportTemplateDto;
import com.kynsoft.report.domain.services.IDBConectionService;
import com.kynsoft.report.domain.services.IJasperReportTemplateService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class CreateJasperReportTemplateCommandHandler implements ICommandHandler<CreateJasperReportTemplateCommand> {

    private final IJasperReportTemplateService service;
    private final IDBConectionService conectionService;

    public CreateJasperReportTemplateCommandHandler(IJasperReportTemplateService service, IDBConectionService conectionService) {
        this.service = service;
        this.conectionService = conectionService;
    }

    @Override
    public void handle(CreateJasperReportTemplateCommand command) {
        DBConectionDto conectionDto = command.getDbConection() != null ? this.conectionService.findById(command.getDbConection()) : null;

        try {
            // 1️⃣ Obtener el contenido del archivo en bytes
            byte[] jrxmlBytes = command.getFile();
            if (jrxmlBytes == null || jrxmlBytes.length == 0) {
                throw new RuntimeException("El archivo JRXML está vacío o no se proporcionó.");
            }

            // 2️⃣ Convertir el byte[] a InputStream
            InputStream jrxmlInputStream = new ByteArrayInputStream(jrxmlBytes);

            // 3️⃣ Compilar el JRXML a un archivo JasperReport
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlInputStream);

            // 4️⃣ Guardar el archivo compilado en una ubicación específica
            String jasperFilePath = "/Users/keimermontes/Downloads/" + command.getCode() + ".jasper";
            File jasperFile = new File(jasperFilePath);
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(jasperFile))) {
                oos.writeObject(jasperReport);
            }

            // ✅ Log para depuración
            System.out.println("Archivo Jasper guardado en: " + jasperFilePath);

            // 5️⃣ Guardar la referencia en base de datos (si aplica)
            this.service.create(new JasperReportTemplateDto(
                    command.getId(),
                    command.getCode(),
                    command.getName(),
                    command.getDescription(),
                    jasperFilePath, // Guarda la ruta del archivo en vez de los bytes
                    command.getType(),
                    command.getParameters(),
                    conectionDto,
                    command.getQuery(),
                    command.getStatus()
            ));

        } catch (JRException | IOException e) {
            throw new RuntimeException("Error al generar el archivo JasperReport", e);
        }
    }
}
