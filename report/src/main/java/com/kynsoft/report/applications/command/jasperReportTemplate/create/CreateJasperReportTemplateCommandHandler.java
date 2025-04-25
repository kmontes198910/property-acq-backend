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
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Base64;
import java.util.UUID;

@Component
public class CreateJasperReportTemplateCommandHandler implements ICommandHandler<CreateJasperReportTemplateCommand> {

    private final IJasperReportTemplateService service;
    private final IDBConnectionService connectionService;

    public CreateJasperReportTemplateCommandHandler(IJasperReportTemplateService service,
                                                    IDBConnectionService conectionService) {
        this.service = service;
        this.connectionService = conectionService;
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

            // Verificar directorio temporal para compilación
            File tempDir = new File("/tmp");
            if (!tempDir.exists() || !tempDir.canWrite()) {
                // Si /tmp no es adecuado, usar el directorio temporal del sistema
                tempDir = new File(System.getProperty("java.io.tmpdir"));
            }
            
            // Asegurar que el directorio temporal es escribible
            if (!tempDir.canWrite()) {
                throw new RuntimeException("No hay acceso de escritura al directorio temporal: " + tempDir.getAbsolutePath());
            }
            
            System.err.println("Usando directorio temporal: " + tempDir.getAbsolutePath());
            
            // Configuración específica para el compilador JDT
            System.setProperty("net.sf.jasperreports.compiler.class", "net.sf.jasperreports.engine.design.JRJdtCompiler");
            System.setProperty("net.sf.jasperreports.compiler.temp.dir", tempDir.getAbsolutePath());
            System.setProperty("net.sf.jasperreports.compiler.classpath", System.getProperty("java.class.path"));
            System.setProperty("net.sf.jasperreports.compiler.useThreadContextClassLoader", "true");
            System.setProperty("net.sf.jasperreports.compiler.xml.validation", "false"); // Desactivar validación XML estricta
            System.setProperty("net.sf.jasperreports.compiler.keep.java.file", "true"); // Mantener archivos Java para depuración
            
            // Para problemas con resumen_hc, intentar ignorar dataset en la compilación
            System.setProperty("net.sf.jasperreports.compiler.max.java.method.size", "65536");
            System.setProperty("net.sf.jasperreports.awt.ignore.missing.font", "true");
            
            // Establecer el class loader adecuado
            ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();
            try {
                Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
                
                // Usar método explícito para cargar primero el diseño, luego compilar
                InputStream jrxmlInputStream = new ByteArrayInputStream(jrxmlBytes);
                JasperDesign jasperDesign = JRXmlLoader.load(jrxmlInputStream);
                
                // Compilar el diseño cargado
                System.err.println("Iniciando compilación del reporte...");
                JasperReport jasperReport;
                try {
                    jasperReport = JasperCompileManager.compileReport(jasperDesign);
                    System.err.println("Reporte compilado correctamente en memoria");
                } catch (JRException e) {
                    System.err.println("Error detallado en compilación: " + e.toString());
                    Throwable cause = e.getCause();
                    while (cause != null) {
                        System.err.println("Causado por: " + cause.toString());
                        cause = cause.getCause();
                    }
                    e.printStackTrace();
                    throw new RuntimeException("Error al compilar el reporte: " + e.getMessage(), e);
                }

                // Convertir el JasperReport a bytes para almacenamiento o transferencia
                ByteArrayOutputStream jasperOutputStream = new ByteArrayOutputStream();
                JRSaver.saveObject(jasperReport, jasperOutputStream);
                byte[] jasperBytes = jasperOutputStream.toByteArray();

                // Imprimir información de depuración
                System.out.println("Archivo Jasper compilado correctamente. Tamaño: " + jasperBytes.length + " bytes");
                
                // Convertir los bytes del jasper a Base64 para almacenamiento en la base de datos
                String jasperBase64 = Base64.getEncoder().encodeToString(jasperBytes);
                System.out.println("Archivo Jasper convertido a Base64. Tamaño: " + jasperBase64.length() + " caracteres");

                JasperReportTemplateDto templateDto = new JasperReportTemplateDto(
                        UUID.randomUUID(),
                        command.getCode(),
                        command.getName(),
                        command.getDescription(),
                       "", // Usar la URL real del archivo subido
                        jasperBase64, // Guardar el archivo .jasper en formato Base64
                        command.getType(),
                        null, // createdAt será establecido por la base de datos
                        dbConnectionDto,
                        Status.ACTIVE
                );

                service.create(templateDto);
                System.err.println("Template guardado con éxito");
            } finally {
                // Restaurar el classloader original
                Thread.currentThread().setContextClassLoader(originalClassLoader);
            }
        } catch (Exception e) {
            System.err.println("Error al crear plantilla: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al crear la plantilla: " + e.getMessage(), e);
        }
    }
}
