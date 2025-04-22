package com.kynsoft.notification.application.query.file.search;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.notification.domain.dto.AFileDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileResponse implements IResponse {
    private UUID id;
    private String name;
    private String url;
    private String objetId;
    private String path;
    private String userId;
    private String userName;
    private String mimeType;
    private Long fileSize;
    private String secureViewUrl;
    private UUID objectId;
    private String objectType;
    private UUID businessId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public FileResponse(AFileDto aFileDto) {
        this.id = aFileDto.getId();
        this.name = aFileDto.getName();
        this.objetId = aFileDto.getObjetId();
        this.path = aFileDto.getPath();
        this.userId = aFileDto.getUserId();
        this.userName = aFileDto.getUserName();
        this.mimeType = aFileDto.getMimeType();
        this.fileSize = aFileDto.getSize();
        this.objectId = aFileDto.getObjectId();
        this.objectType = aFileDto.getObjectType();
        this.businessId = aFileDto.getBusinessId();
        this.createdAt = aFileDto.getCreatedAt();
        this.updatedAt = aFileDto.getUpdatedAt();
        this.url = aFileDto.getUrl();
        this.secureViewUrl = aFileDto.getSecureViewUrl();
    }
}