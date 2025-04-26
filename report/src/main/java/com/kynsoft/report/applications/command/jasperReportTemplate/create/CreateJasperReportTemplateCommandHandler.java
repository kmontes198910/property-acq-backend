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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.UUID;

/**
 * Manejador para crear plantillas de reportes Jasper.
 * Se encarga de compilar el archivo JRXML y almacenarlo como un objeto Jasper
 * en formato Base64 dentro de la base de datos.
 */
@Component
public class CreateJasperReportTemplateCommandHandler implements ICommandHandler<CreateJasperReportTemplateCommand> {

    // ================= CONSTANTES =================
    
    private static final Logger logger = LoggerFactory.getLogger(CreateJasperReportTemplateCommandHandler.class);
    
    // ================= DEPENDENCIAS =================
    
    private final IJasperReportTemplateService service;
    private final IDBConnectionService connectionService;

    // ================= CONSTRUCTOR =================
    
    /**
     * Constructor con inyección de dependencias
     */
    public CreateJasperReportTemplateCommandHandler(
            IJasperReportTemplateService service, 
            IDBConnectionService connectionService, 
            IFileUploadService fileUploadService) {
        this.service = service;
        this.connectionService = connectionService;
    }

    // ================= MÉTODO PRINCIPAL =================
    
    @Override
    public void handle(CreateJasperReportTemplateCommand command) {
        try {
            // Obtener y normalizar la conexión a la base de datos
            DBConnectionDto dbConnectionDto = getDatabaseConnection(command);
            
            // Procesar archivo JRXML
            String jasperBase64 = processJrxmlFile(command.getFile());
            
            // Crear el objeto DTO
            JasperReportTemplateDto templateDto = createTemplateDto(command, dbConnectionDto, jasperBase64);
            
            // Guardar en la base de datos
            service.create(templateDto);
            logger.info("Template de reporte Jasper creado con éxito");
        } catch (Exception e) {
            logger.error("Error al crear plantilla: {}", e.getMessage(), e);
            throw new RuntimeException("Error al crear plantilla de reporte", e);
        }
    }

    // ================= MÉTODOS DE NEGOCIO =================
    
    /**
     * Obtiene y normaliza la conexión a la base de datos
     */
    private DBConnectionDto getDatabaseConnection(CreateJasperReportTemplateCommand command) {
        DBConnectionDto dbConnectionDto = command.getDbConection() != null 
                ? this.connectionService.findById(command.getDbConection()) 
                : null;
        
        // Normalizar la URL de la conexión si existe
        if (dbConnectionDto != null && dbConnectionDto.getUrl() != null) {
            String normalizedUrl = dbConnectionDto.getUrl().toLowerCase();
            dbConnectionDto.setUrl(normalizedUrl);
            logger.debug("URL de conexión normalizada: {}", normalizedUrl);
        }
        
        return dbConnectionDto;
    }

    /**
     * Crea el objeto DTO para la plantilla
     */
    private JasperReportTemplateDto createTemplateDto(
            CreateJasperReportTemplateCommand command, 
            DBConnectionDto dbConnectionDto, 
            String jasperBase64) {
        
        return new JasperReportTemplateDto(
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
    }

    // ================= PROCESAMIENTO DE ARCHIVOS JRXML =================
    
    /**
     * Procesa el archivo JRXML y lo convierte en un objeto JasperReport en Base64
     */
    private String processJrxmlFile(byte[] jrxmlBytes) throws Exception {
        // Validar datos de entrada
        if (jrxmlBytes == null || jrxmlBytes.length == 0) {
            throw new IllegalArgumentException("El archivo JRXML está vacío o no se proporcionó");
        }
        
        logger.info("Procesando archivo JRXML. Tamaño: {} bytes", jrxmlBytes.length);
        
        // Compilar el archivo JRXML
        JasperReport jasperReport = compileJrxmlFile(jrxmlBytes);
        
        // Convertir a bytes y luego a Base64
        String jasperBase64 = convertJasperReportToBase64(jasperReport);
        
        return jasperBase64;
    }

    /**
     * Compila un archivo JRXML
     */
    private JasperReport compileJrxmlFile(byte[] jrxmlBytes) throws JRException {
        // Configuración para el compilador
        configureJasperCompiler();
        
        // Compilar el reporte
        try (InputStream jrxmlInputStream = new ByteArrayInputStream(jrxmlBytes)) {
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlInputStream);
            logger.info("Reporte compilado correctamente en memoria");
            return jasperReport;
        } catch (JRException e) {
            logger.error("Error al compilar el reporte", e);
            throw e;
        } catch (Exception e) {
            logger.error("Error al leer el archivo JRXML", e);
            throw new RuntimeException("Error al leer el archivo JRXML", e);
        }
    }

    // ================= MÉTODOS UTILITARIOS =================
    
    /**
     * Configura el compilador de Jasper
     */
    private void configureJasperCompiler() {
        System.setProperty("net.sf.jasperreports.compiler.class", 
                "net.sf.jasperreports.engine.design.JRJdtCompiler");
        System.setProperty("net.sf.jasperreports.compiler.useThreadContextClassLoader", "true");
        Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
    }

    /**
     * Convierte un JasperReport a Base64
     */
    private String convertJasperReportToBase64(JasperReport jasperReport) throws JRException {
        try (ByteArrayOutputStream jasperOutputStream = new ByteArrayOutputStream()) {
            // Guardar el objeto JasperReport como bytes
            JRSaver.saveObject(jasperReport, jasperOutputStream);
            byte[] jasperBytes = jasperOutputStream.toByteArray();
            
            // Información de depuración
            logger.info("Archivo Jasper compilado correctamente. Tamaño: {} bytes", jasperBytes.length);
            
            // Convertir a Base64
            String jasperBase64 = Base64.getEncoder().encodeToString(jasperBytes);
            logger.info("Archivo Jasper convertido a Base64. Tamaño: {} caracteres", jasperBase64.length());
            
            return jasperBase64;
        } catch (Exception e) {
            logger.error("Error al convertir JasperReport a Base64", e);
            throw new JRException("Error al convertir JasperReport a Base64", e);
        }
    }
}
