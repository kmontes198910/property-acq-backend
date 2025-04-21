package com.kynsoft.notification.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * DTO para la carga de archivos en formato base64
 */
@Setter
@Getter
public class FileBase64UploadDto {
    
    private String base64Content;
    private String fileName;
    private String objectId;
    private String folderPath;
    private UUID businessId; // ID del negocio al que pertenece el archivo

    public FileBase64UploadDto() {
    }

    public FileBase64UploadDto(String base64Content, String fileName, String objectId, String folderPath) {
        this.base64Content = base64Content;
        this.fileName = fileName;
        this.objectId = objectId;
        this.folderPath = folderPath;
    }
    
    public FileBase64UploadDto(String base64Content, String fileName, String objectId, String folderPath, UUID businessId) {
        this.base64Content = base64Content;
        this.fileName = fileName;
        this.objectId = objectId;
        this.folderPath = folderPath;
        this.businessId = businessId;
    }

}