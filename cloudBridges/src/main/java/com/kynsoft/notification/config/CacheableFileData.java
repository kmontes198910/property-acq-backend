package com.kynsoft.notification.config;

import java.io.Serializable;

/**
 * Clase de datos cacheables para archivos
 */
public  class CacheableFileData implements Serializable {
    private static final long serialVersionUID = 1L;

    private final byte[] fileBytes;
    private final String fileName;
    private final String mimeType;

    public CacheableFileData(byte[] fileBytes, String fileName, String mimeType) {
        this.fileBytes = fileBytes;
        this.fileName = fileName;
        this.mimeType = mimeType;
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public String getFileName() {
        return fileName;
    }

    public String getMimeType() {
        return mimeType;
    }
}
