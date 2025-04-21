package com.kynsoft.notification.application.command.file.saveFileS3;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.UUID;

/**
 * Comando para subir un archivo a Amazon S3
 * Contiene toda la información necesaria para el proceso de subida
 */
@Getter
@Setter
public class SaveFileS3Command implements ICommand {
    private final MultipartFile multipartFile;
    private UUID fileId;
    private String url;
    private String viewUrl;
    private final String fileName;
    private final String objectId;
    private final String folderPath;
    private String userId;
    private String userName;
    private Long fileSize;
    private UUID businessId; // Campo para almacenar el ID de negocio



    /**
     * Constructor completo con información del usuario y empresa
     * @param multipartFile Archivo a subir
     * @param fileName Nombre del archivo
     * @param objectId ID del objeto relacionado
     * @param folderPath Ruta de la carpeta donde se guardará
     * @param userId ID del usuario que sube el archivo
     * @param userName Nombre del usuario que sube el archivo
     * @param businessId ID del negocio al que pertenece el archivo
     */
    public SaveFileS3Command(MultipartFile multipartFile, String fileName, String objectId, String folderPath, 
                           String userId, String userName, UUID businessId) {
        this.multipartFile = Objects.requireNonNull(multipartFile, "El archivo no puede ser nulo");
        this.fileName = Objects.requireNonNull(fileName, "El nombre de archivo no puede ser nulo");
        this.objectId = objectId; // Puede ser nulo
        this.folderPath = folderPath; // Puede ser nulo
        this.userId = userId;
        this.userName = userName;
        this.businessId = businessId;
        this.fileSize = multipartFile.getSize(); // Capturar el tamaño automáticamente
    }

    /**
     * Obtiene el archivo a subir
     * @return El archivo MultipartFile
     */
    public MultipartFile getFile() {
        return multipartFile;
    }

    /**
     * Obtiene el nombre del archivo
     * @return Nombre del archivo
     */
    public String getName() {
        return fileName;
    }

    @Override
    public ICommandMessage getMessage() {
        return new SaveFileS3Message(fileId, url, viewUrl, fileSize);
    }
}
