package com.kynsoft.notification.application.query.file.search;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.notification.domain.dto.AFileDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class FileResponse implements IResponse {
    private UUID id;
    private String name;
    private String url;
    private String viewUrl;
    private String objetId;
    private String path;
    private String userId;
    private String userName;
    private String mimeType;
    private Long fileSize;  // Cambiado de size a fileSize para coincidir con el JSON
    private String secureViewUrl;
    private UUID objectId;
    private String objectType;
    private UUID businessId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // URL base para los endpoints seguros (se inyecta desde configuración)
    private static String baseApiUrl;

    @Value("${app.api.base-url:http://localhost:8080}")
    public void setBaseApiUrl(String url) {
        FileResponse.baseApiUrl = url;
    }

    public FileResponse() {
    }

    public FileResponse(AFileDto aFileDto) {
        this.id = aFileDto.getId();
        this.name = aFileDto.getName();
        
        // Generar URLs seguras usando el ID del archivo
        if (baseApiUrl != null) {
            this.url = baseApiUrl + "/api/secure/files/" + aFileDto.getId();
            this.viewUrl = baseApiUrl + "/api/secure/files/view/" + aFileDto.getId();
        } else {
            // Fallback a la URL del servicio original (solo para debug)
            this.url = aFileDto.getUrl();
            this.viewUrl = aFileDto.getUrl();
        }
        
        this.objetId = aFileDto.getObjetId();
        this.path = aFileDto.getPath();
        this.userId = aFileDto.getUserId();
        this.userName = aFileDto.getUserName();
        this.mimeType = aFileDto.getMimeType();
        this.fileSize = aFileDto.getSize();  // Mapear size del DTO a fileSize en la respuesta
        this.objectId = aFileDto.getObjectId();
        this.objectType = aFileDto.getObjectType();
        this.businessId = aFileDto.getBusinessId();
        this.createdAt = aFileDto.getCreatedAt();
        this.updatedAt = aFileDto.getUpdatedAt();
        this.secureViewUrl = aFileDto.getSecureViewUrl();
    }
}