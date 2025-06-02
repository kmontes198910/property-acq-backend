package com.kynsoft.report.applications.command.jasperReportTemplate.create;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Component
public class CreateJasperReportTemplateCommandHandler implements ICommandHandler<CreateJasperReportTemplateCommand> {
    private static final Logger logger = LoggerFactory.getLogger(CreateJasperReportTemplateCommandHandler.class);
    
    private final IJasperReportTemplateService service;
    private final IDBConnectionService connectionService;
    
    @Value("${jasper.reports.output.path:/app/jasper-output}")
    private String jasperOutputPath;

    public CreateJasperReportTemplateCommandHandler(IJasperReportTemplateService service,
                                                    IDBConnectionService conectionService) {
        this.service = service;
        this.connectionService = conectionService;
    }

    @Override
    public void handle(CreateJasperReportTemplateCommand command) {
        logger.info("Iniciando creación de plantilla de reporte: {}", command.getName());
        
        try {
            // Validar y obtener el contenido del archivo
            validateCommand(command);
            
            byte[] jrxmlBytes = command.getFile();
            File tempDir = setupTempDirectory();
            configureJasperProperties(tempDir);
            
            // Compilar el reporte
            JasperReport jasperReport = compileReport(jrxmlBytes);
            byte[] jasperBytes = convertToBytes(jasperReport);
            
            // Guardar el archivo y crear DTO
            String jasperFilePath = saveJasperFile(command, jasperBytes);
            String jasperBase64 = Base64.getEncoder().encodeToString(jasperBytes);
            
            // Crear y guardar la plantilla
            DBConnectionDto dbConnectionDto = command.getDbConection() != null ? 
                    this.connectionService.findById(command.getDbConection()) : null;
            
            JasperReportTemplateDto templateDto = createTemplateDto(
                    command, jasperFilePath, jasperBase64, dbConnectionDto);
            
            service.create(templateDto);
            logger.info("Plantilla de reporte guardada con éxito: {}", command.getName());
            
        } catch (Exception e) {
            logger.error("Error al crear plantilla: {}", e.getMessage(), e);
            throw new RuntimeException("Error al crear la plantilla: " + e.getMessage(), e);
        }
    }
    
    private void validateCommand(CreateJasperReportTemplateCommand command) {
        if (command.getFile() == null || command.getFile().length == 0) {
            throw new IllegalArgumentException("El archivo JRXML está vacío o no se proporcionó");
        }
        
        if (command.getCode() == null || command.getCode().isEmpty()) {
            throw new IllegalArgumentException("El código de la plantilla es obligatorio");
        }
        
        if (command.getName() == null || command.getName().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la plantilla es obligatorio");
        }
    }
    
    private File setupTempDirectory() {
        File tempDir = new File("/tmp");
        if (!tempDir.exists() || !tempDir.canWrite()) {
            tempDir = new File(System.getProperty("java.io.tmpdir"));
        }
        
        if (!tempDir.canWrite()) {
            throw new RuntimeException("No hay acceso de escritura al directorio temporal: " + tempDir.getAbsolutePath());
        }
        
        logger.debug("Usando directorio temporal: {}", tempDir.getAbsolutePath());
        return tempDir;
    }
    
    private void configureJasperProperties(File tempDir) {
        // Propiedades esenciales para la compilación
        System.setProperty("net.sf.jasperreports.compiler.class", "net.sf.jasperreports.engine.design.JRJdtCompiler");
        System.setProperty("net.sf.jasperreports.compiler.temp.dir", tempDir.getAbsolutePath());
        System.setProperty("net.sf.jasperreports.compiler.classpath", System.getProperty("java.class.path"));
        System.setProperty("net.sf.jasperreports.compiler.useThreadContextClassLoader", "true");
        
        // Propiedades para manejar casos específicos
        System.setProperty("net.sf.jasperreports.compiler.xml.validation", "false");
        System.setProperty("net.sf.jasperreports.compiler.max.java.method.size", "65536");
        System.setProperty("net.sf.jasperreports.awt.ignore.missing.font", "true");
    }
    
    private JasperReport compileReport(byte[] jrxmlBytes) throws JRException {
        ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();
        
        try {
            Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
            
            InputStream jrxmlInputStream = new ByteArrayInputStream(jrxmlBytes);
            JasperDesign jasperDesign = JRXmlLoader.load(jrxmlInputStream);
            
            logger.debug("Iniciando compilación del reporte...");
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            logger.debug("Reporte compilado correctamente en memoria");
            
            return jasperReport;
        } catch (JRException e) {
            logCompilationError(e);
            throw e;
        } finally {
            Thread.currentThread().setContextClassLoader(originalClassLoader);
        }
    }
    
    private void logCompilationError(JRException e) {
        logger.error("Error en compilación: {}", e.toString());
        Throwable cause = e.getCause();
        while (cause != null) {
            logger.error("Causado por: {}", cause.toString());
            cause = cause.getCause();
        }
    }
    
    private byte[] convertToBytes(JasperReport jasperReport) throws JRException {
        ByteArrayOutputStream jasperOutputStream = new ByteArrayOutputStream();
        JRSaver.saveObject(jasperReport, jasperOutputStream);
        byte[] jasperBytes = jasperOutputStream.toByteArray();
        logger.debug("Archivo Jasper compilado. Tamaño: {} bytes", jasperBytes.length);
        return jasperBytes;
    }
    
    private String saveJasperFile(CreateJasperReportTemplateCommand command, byte[] jasperBytes) {
        // Verificar y crear directorio de salida
        File outputDir = new File(jasperOutputPath);
        if (!outputDir.exists() && !outputDir.mkdirs()) {
            logger.warn("No se pudo crear el directorio: {}", jasperOutputPath);
        }
        
        // Crear nombre de archivo sin espacios ni caracteres especiales
        String sanitizedName = command.getName().replaceAll("[^a-zA-Z0-9_-]", "_");
        String jasperFileName = command.getCode() + "_" + sanitizedName + ".jasper";
        Path jasperFilePath = Paths.get(jasperOutputPath, jasperFileName);
        
        try {
            Files.write(jasperFilePath, jasperBytes);
            logger.info("Archivo Jasper guardado en: {}", jasperFilePath);
            return jasperFilePath.toString();
        } catch (IOException e) {
            logger.warn("Error al guardar el archivo Jasper: {}", e.getMessage());
            // Continuamos aunque haya error al guardar el archivo
            return null;
        }
    }
    
    private JasperReportTemplateDto createTemplateDto(
            CreateJasperReportTemplateCommand command, 
            String jasperFilePath, 
            String jasperBase64, 
            DBConnectionDto dbConnectionDto) {
        
        return new JasperReportTemplateDto(
            UUID.randomUUID(),
            command.getCode(),
            command.getName(),
            command.getDescription(),
            jasperFilePath, // Ruta del archivo Jasper o null si falló el guardado
            jasperBase64,   // Base64 para respaldo
            command.getType(),
            null,           // createdAt será establecido por la base de datos
            dbConnectionDto,
            Status.ACTIVE
        );
    }
}
