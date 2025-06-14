package com.kynsoft.notification.config;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Clase de datos cacheables para archivos
 */
@Getter
public  class CacheableFileData implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final byte[] fileBytes;
    private final String fileName;
    private final String mimeType;

    public CacheableFileData(byte[] fileBytes, String fileName, String mimeType) {
        this.fileBytes = fileBytes;
        this.fileName = fileName;
        this.mimeType = mimeType;
    }

}
