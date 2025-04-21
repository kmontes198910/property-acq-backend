package com.kynsoft.notification.application.query.file.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.notification.domain.dto.AFileDto;
import com.kynsoft.notification.domain.service.IAFileService;
import com.kynsoft.notification.domain.service.IAmazonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DeleteFileCommandHandler implements ICommandHandler<DeleteFileCommand> {

    private static final Logger logger = LoggerFactory.getLogger(DeleteFileCommandHandler.class);
    private final IAFileService serviceImpl;
    private final IAmazonClient amazonClient;

    public DeleteFileCommandHandler(IAFileService serviceImpl, IAmazonClient amazonClient) {
        this.serviceImpl = serviceImpl;
        this.amazonClient = amazonClient;
    }

    @Override
    public void handle(DeleteFileCommand command) {
        // 1. Buscar el archivo en la base de datos
        AFileDto object = this.serviceImpl.findById(command.getId());
        
        if (object == null) {
            logger.warn("No se encontró el archivo con ID: {}", command.getId());
            command.setResult(false);
            return;
        }
        
        try {
            // 2. Eliminar primero el archivo físico de S3
            logger.info("Eliminando archivo físico de S3: {}", object.getUrl());
            amazonClient.delete(object.getUrl());
            
            // 3. Eliminar el registro de la base de datos
            logger.info("Eliminando registro de la base de datos con ID: {}", object.getId());
            serviceImpl.delete(object);
            
            logger.info("Archivo eliminado exitosamente: {} ({})", object.getName(), object.getId());
            command.setResult(true);
        } catch (Exception e) {
            logger.error("Error al eliminar el archivo: {}", e.getMessage(), e);
            command.setResult(false);
            throw e;
        }
    }
}
