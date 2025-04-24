package com.kynsof.treatments.application.command.result.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * DTO para la carga de archivos en formato base64
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public   class FileBase64UploadDto {
    private String base64Content;
    private String fileName;
    private String objectId;
    private String folderPath;
    private UUID businessId;

}