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

@Component
public class UpdateJasperReportTemplateCommandHandler implements ICommandHandler<UpdateJasperReportTemplateCommand> {

    private static final Logger logger = LoggerFactory.getLogger(UpdateJasperReportTemplateCommandHandler.class);
    
    private final IJasperReportTemplateService service;
    private final IDBConnectionService conectionService;

    public UpdateJasperReportTemplateCommandHandler(IJasperReportTemplateService service, 
                                                  IDBConnectionService conectionService) {
        this.service = service;
        this.conectionService = conectionService;
    }

    @Override
    public void handle(UpdateJasperReportTemplateCommand command) {
        RulesChecker.checkRule(new ValidateObjectNotNullRule<>(command.getId(), "id", "JasperReportTemplate ID cannot be null."));
        JasperReportTemplateDto update = this.service.findById(command.getId());
        
        // Actualizar los campos básicos
        update.setTemplateContentUrl(command.getFile());
        UpdateIfNotNull.updateIfNotNull(update::setTemplateCode, command.getCode());
        UpdateIfNotNull.updateIfNotNull(update::setTemplateDescription, command.getDescription());
        UpdateIfNotNull.updateIfNotNull(update::setTemplateName, command.getName());
        update.setType(command.getType());
        this.updateConection(update::setDbConection, command.getDbConection(), 
                          update.getDbConection() != null ? update.getDbConection().getId() : null);
        updateStatus(update::setStatus, command.getStatus(), update.getStatus());

        // Si hay un nuevo archivo, compilarlo y actualizarlo
        if (command.getFileBytes() != null && command.getFileBytes().length > 0) {
            try {
                logger.info("Procesando nuevo archivo .jrxml para actualización");
                
                // Configuración para el compilador de Jasper
                System.setProperty("net.sf.jasperreports.compiler.class", "net.sf.jasperreports.engine.design.JRJdtCompiler");
                System.setProperty("net.sf.jasperreports.compiler.useThreadContextClassLoader", "true");
                
                // Establecer el class loader adecuado
                Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
                
                // Compilar el reporte
                InputStream jrxmlInputStream = new ByteArrayInputStream(command.getFileBytes());
                JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlInputStream);
                logger.info("Reporte compilado correctamente en memoria");
                
                // Convertir el JasperReport a bytes para almacenamiento
                ByteArrayOutputStream jasperOutputStream = new ByteArrayOutputStream();
                JRSaver.saveObject(jasperReport, jasperOutputStream);
                byte[] jasperBytes = jasperOutputStream.toByteArray();
                
                // Convertir a Base64 para guardar en la base de datos
                String jasperBase64 = Base64.getEncoder().encodeToString(jasperBytes);
                logger.info("Archivo Jasper actualizado y convertido a Base64. Tamaño: {} caracteres", jasperBase64.length());
                
                // Actualizar el campo jasperContentBase64 con el nuevo contenido
                update.setJasperContentBase64(jasperBase64);
            } catch (JRException e) {
                logger.error("Error al compilar el reporte Jasper", e);
                throw new RuntimeException("Error al compilar el reporte Jasper: " + e.getMessage(), e);
            } catch (Exception e) {
                logger.error("Error inesperado al procesar el archivo", e);
                throw new RuntimeException("Error al procesar el archivo: " + e.getMessage(), e);
            }
        }
        
        // Actualizar el objeto en la base de datos
        this.service.update(update);
        logger.info("Template de reporte Jasper actualizado con éxito");
    }

    private void updateConection(Consumer<DBConnectionDto> setter, UUID newValue, UUID oldValue) {
        if (newValue != null && !newValue.equals(oldValue)) {
            DBConnectionDto conectionDto = this.conectionService.findById(newValue);
            setter.accept(conectionDto);
        }
    }

    private void updateStatus(Consumer<Status> setter, Status newValue, Status oldValue) {
        if (newValue != null && !newValue.equals(oldValue)) {
            setter.accept(newValue);
        }
    }
}
