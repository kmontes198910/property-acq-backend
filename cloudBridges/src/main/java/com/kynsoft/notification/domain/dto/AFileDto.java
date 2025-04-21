package com.kynsoft.notification.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AFileDto {
    private UUID id;
    private String name;
    private String url;
    private String objetId;
    private String path;
    private String userId;
    private String userName;
    private String mimeType;
    private Long size;
    private UUID objectId;
    private String objectType;
    private String fileContent;
    private String secureViewUrl;
    private UUID businessId; // Campo para almacenar la empresa (ID de negocio)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AFileDto(String name, String url) {
        this.name = name;
        this.url = url;
    }
    
    public AFileDto(UUID id, String name, String url, String objetId, String path) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.objetId = objetId;
        this.path = path;
    }
    
    public AFileDto(UUID id, String name, String url, String objetId, String path, 
                   String userId, String userName) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.objetId = objetId;
        this.path = path;
        this.userId = userId;
        this.userName = userName;
    }
    
    public AFileDto(UUID id, String name, String url, String objetId, String path, 
                   String userId, String userName, UUID businessId) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.objetId = objetId;
        this.path = path;
        this.userId = userId;
        this.userName = userName;
        this.businessId = businessId;
    }
    
    public AFileDto(UUID id, String name, String url, String objetId, String path, 
                   String userId, String userName, String mimeType, UUID businessId) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.objetId = objetId;
        this.path = path;
        this.userId = userId;
        this.userName = userName;
        this.mimeType = mimeType;
        this.businessId = businessId;
    }
}
