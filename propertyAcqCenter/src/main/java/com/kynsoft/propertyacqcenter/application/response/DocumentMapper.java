package com.kynsoft.propertyacqcenter.application.response;

public class DocumentMapper {

    public static AdquisitionDocumentResponse mapDocumentField(String documentField) {
        if (documentField == null || documentField.isEmpty()) {
            return null;
        }

        String[] parts = documentField.split("\\|");
        return AdquisitionDocumentResponse.builder()
                .filePath(parts[0])
                .fileName(parts[1])
                .build();
    }
}
