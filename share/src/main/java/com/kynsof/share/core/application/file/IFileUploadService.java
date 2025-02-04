package com.kynsof.share.core.application.file;

public interface IFileUploadService {
    UploadResponse uploadJasperToS3(byte[] jasperBytes, String fileName, String folderPath);
}
