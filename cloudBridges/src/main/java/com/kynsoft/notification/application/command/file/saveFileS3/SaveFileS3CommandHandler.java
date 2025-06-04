package com.kynsoft.notification.application.command.file.saveFileS3;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.notification.domain.dto.AFileDto;
import com.kynsoft.notification.domain.service.IAFileService;
import com.kynsoft.notification.infrastructure.service.AmazonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class SaveFileS3CommandHandler implements ICommandHandler<SaveFileS3Command> {

    private static final Logger logger = LoggerFactory.getLogger(SaveFileS3CommandHandler.class);
    private final AmazonClient amazonClient;
    private final IAFileService fileService;

    // Tipos MIME permitidos (puedes ajustar según tus necesidades)
    private static final List<String> ALLOWED_MIME_TYPES = Arrays.asList(
            "image/jpeg", "image/png", "image/gif", "application/pdf",
            "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
            "text/plain", "application/zip"
    );

    // Tamaño máximo de archivo (10 MB)
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    @Value("${app.secure-files.download-path:/api/secure/files/}")
    private String secureFilesPath;

    @Value("${app.secure-files.view-path:/api/secure/files/view/}")
    private String secureViewPath;

    public SaveFileS3CommandHandler(AmazonClient amazonClient, IAFileService fileService) {
        this.amazonClient = amazonClient;
        this.fileService = fileService;
    }

    @Override
    public void handle(SaveFileS3Command command) {
        try {
            MultipartFile file = command.getFile();

            // Validar el archivo antes de procesar
            validateFile(file);

            // Guardar el archivo en S3
            String fileUrl = amazonClient.save(file, command.getFolderPath());
            logger.info("Archivo guardado en S3: {}", fileUrl);

            // Capturar el tamaño del archivo
            Long fileSize = file.getSize();
            command.setFileSize(fileSize);
            logger.info("Tamaño del archivo: {} bytes", fileSize);

            // Crear el DTO del archivo con la URL de S3
            AFileDto fileDto = new AFileDto();
            
            // Generar un UUID para el archivo
            UUID fileUuid = UUID.randomUUID();
            fileDto.setId(fileUuid);
            fileDto.setUrl(fileUrl);
            fileDto.setSize(fileSize);
            fileDto.setName(command.getName());
            fileDto.setMimeType(file.getContentType());
            fileDto.setPath(command.getFolderPath());
            fileDto.setBusinessId(command.getBusinessId());

            // Añadimos la información del usuario
            fileDto.setUserId(command.getUserId());
            fileDto.setUserName(command.getUserName());
            
            // Añadimos la información de la empresa (ID de negocio)
            fileDto.setBusinessId(command.getBusinessId());
            
            logger.info("Procesando archivo subido por usuario: {} (ID: {}), Business ID: {}",
                    command.getUserName(), command.getUserId(), command.getBusinessId());

            // Si se proporciona un objectId, convertirlo en UUID y asignarlo
            if (command.getObjectId() != null && !command.getObjectId().isEmpty()) {
                try {
                    UUID objectUuid = UUID.fromString(command.getObjectId());
                    fileDto.setObjectId(objectUuid);
                    // Para mantener compatibilidad con código antiguo
                    fileDto.setObjetId(command.getObjectId());
                } catch (IllegalArgumentException e) {
                    // Si no es un UUID válido, usarlo como objectType
                    fileDto.setObjectType(command.getObjectId());
                    // Para mantener compatibilidad con código antiguo, dejamos objetId como null
                }
            }

            // Guardar el archivo en la base de datos
            UUID id = fileService.create(fileDto);
            if (id == null) {
                throw new RuntimeException("Error al guardar el archivo en la base de datos");
            }

            logger.info("Archivo guardado en base de datos con ID: {}", id);
//
            // Generar la URL segura para el cliente (no exponer la URL de S3)
            String secureUrl = secureFilesPath + id;
            String secureViewUrl = secureViewPath + id;
            fileDto.setSecureViewUrl(secureViewUrl);
            fileService.update(fileDto);
            command.setFileId(id);
            command.setUrl(secureUrl);
            command.setViewUrl(secureViewUrl);
        } catch (IOException e) {
            logger.error("Error de E/S al guardar el archivo: {}", e.getMessage(), e);
            throw new RuntimeException("Error al guardar el archivo: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Error inesperado al procesar el archivo: {}", e.getMessage(), e);
            throw new RuntimeException("Error al procesar el archivo: " + e.getMessage(), e);
        }
    }

    /**
     * Valida que el archivo cumpla con los requisitos
     *
     * @param file Archivo a validar
     * @throws IllegalArgumentException Si el archivo no cumple los requisitos
     */
    private void validateFile(MultipartFile file) {
        // Validar que el archivo no sea nulo
        if (file == null) {
            throw new IllegalArgumentException("El archivo no puede ser nulo");
        }

        // Validar que el archivo no esté vacío
        if (file.isEmpty() || file.getSize() == 0) {
            throw new IllegalArgumentException("El archivo está vacío");
        }

        // Validar el tamaño del archivo
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException(
                    "El archivo excede el tamaño máximo permitido (" +
                    (MAX_FILE_SIZE / (1024 * 1024)) + " MB)");
        }

        // Validar el tipo MIME
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_MIME_TYPES.contains(contentType)) {
            throw new IllegalArgumentException(
                    "Tipo de archivo no permitido. Tipos permitidos: " +
                    String.join(", ", ALLOWED_MIME_TYPES));
        }
    }
}
