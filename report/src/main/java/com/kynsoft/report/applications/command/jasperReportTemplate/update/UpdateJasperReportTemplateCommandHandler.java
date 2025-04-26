package com.kynsoft.report.applications.command.jasperReportTemplate.update;

import com.kynsof.share.core.application.FileServices.IFileUploadService;
import com.kynsof.share.core.domain.RulesChecker;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.rules.ValidateObjectNotNullRule;
import com.kynsof.share.utils.UpdateIfNotNull;
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
import java.util.function.Consumer;

/**
 * Manejador para actualizar plantillas de reportes Jasper.
 * Se encarga de actualizar los datos básicos de la plantilla
 * y de compilar y almacenar el archivo JRXML si es proporcionado.
 */
@Component
public class UpdateJasperReportTemplateCommandHandler implements ICommandHandler<UpdateJasperReportTemplateCommand> {

    private static final Logger logger = LoggerFactory.getLogger(UpdateJasperReportTemplateCommandHandler.class);
    
    // Servicios
    private final IJasperReportTemplateService service;
    private final IDBConnectionService conectionService;
    private final IFileUploadService fileUploadService;

    /**
     * Constructor con inyección de dependencias
     */
    public UpdateJasperReportTemplateCommandHandler(
            IJasperReportTemplateService service, 
            IDBConnectionService conectionService,
            IFileUploadService fileUploadService) {
        this.service = service;
        this.conectionService = conectionService;
        this.fileUploadService = fileUploadService;
    }

    @Override
    public void handle(UpdateJasperReportTemplateCommand command) {
        // Validar datos de entrada
        validateCommand(command);
        
        // Obtener la plantilla existente
        JasperReportTemplateDto template = service.findById(command.getId());
        
        // Actualizar datos básicos
        updateBasicInfo(template, command);
        
        // Procesar archivo JRXML si existe
        processJrxmlFile(template, command);
        
        // Guardar cambios
        service.update(template);
        logger.info("Template de reporte Jasper actualizado con éxito");
    }

    /**
     * Valida los datos del comando
     */
    private void validateCommand(UpdateJasperReportTemplateCommand command) {
        RulesChecker.checkRule(new ValidateObjectNotNullRule<>(
                command.getId(), 
                "id", 
                "JasperReportTemplate ID cannot be null."));
    }

    /**
     * Actualiza la información básica de la plantilla
     */
    private void updateBasicInfo(JasperReportTemplateDto template, UpdateJasperReportTemplateCommand command) {
        // Actualizar URL del contenido
        template.setTemplateContentUrl(command.getFile());
        
        // Actualizar campos básicos si no son nulos
        UpdateIfNotNull.updateIfNotNull(template::setTemplateCode, command.getCode());
        UpdateIfNotNull.updateIfNotNull(template::setTemplateDescription, command.getDescription());
        UpdateIfNotNull.updateIfNotNull(template::setTemplateName, command.getName());
        template.setType(command.getType());
        
        // Actualizar conexión a base de datos
        updateDatabaseConnection(template, command);
        
        // Actualizar estado
        updateStatus(template::setStatus, command.getStatus(), template.getStatus());
    }

    /**
     * Actualiza la conexión a base de datos si es proporcionada
     */
    private void updateDatabaseConnection(JasperReportTemplateDto template, UpdateJasperReportTemplateCommand command) {
        if (command.getDbConection() != null) {
            DBConnectionDto dbConnectionDto = conectionService.findById(command.getDbConection());
            
            // Normalizar URL de conexión
            if (dbConnectionDto != null && dbConnectionDto.getUrl() != null) {
                String normalizedUrl = dbConnectionDto.getUrl().toLowerCase();
                dbConnectionDto.setUrl(normalizedUrl);
                template.setDbConection(dbConnectionDto);
            }
        }
    }

    /**
     * Procesa el archivo JRXML si existe
     */
    private void processJrxmlFile(JasperReportTemplateDto template, UpdateJasperReportTemplateCommand command) {
        if (command.getFileBytes() == null || command.getFileBytes().length == 0) {
            return;
        }
        
        try {
            logger.info("Procesando nuevo archivo .jrxml para actualización");
            
            // Compilar el archivo JRXML
            JasperReport jasperReport = compileJrxmlFile(command.getFileBytes());
            
            // Convertir a bytes y luego a Base64
            String jasperBase64 = convertJasperReportToBase64(jasperReport);
            
            // Actualizar la plantilla
            template.setJasperContentBase64(jasperBase64);
        } catch (Exception e) {
            logger.error("Error al procesar el archivo JRXML", e);
            throw new RuntimeException("Error al procesar el archivo: " + e.getMessage(), e);
        }
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

    /**
     * Método auxiliar para actualizar una conexión
     */
    private void updateConection(Consumer<DBConnectionDto> setter, UUID newValue, UUID oldValue) {
        if (newValue != null && !newValue.equals(oldValue)) {
            DBConnectionDto conectionDto = this.conectionService.findById(newValue);
            setter.accept(conectionDto);
        }
    }

    /**
     * Método auxiliar para actualizar un estado
     */
    private void updateStatus(Consumer<Status> setter, Status newValue, Status oldValue) {
        if (newValue != null && !newValue.equals(oldValue)) {
            setter.accept(newValue);
        }
    }
}
