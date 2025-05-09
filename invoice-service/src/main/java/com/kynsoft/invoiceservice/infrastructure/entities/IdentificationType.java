package com.kynsoft.invoiceservice.infrastructure.entities;

import lombok.Getter;

/**
 * Enumeration of identification types according to SRI Ecuador
 */
@Getter
public enum IdentificationType {
    RUC("04"),
    CEDULA("05"),
    PASAPORTE("06"),
    CONSUMIDOR_FINAL("07"),
    IDENTIFICACION_EXTERIOR("08");
    
    private final String code;
    
    IdentificationType(String code) {
        this.code = code;
    }

    /**
     * Get IdentificationType from code
     * @param code the identification type code
     * @return the corresponding IdentificationType or null if not found
     */
    public static IdentificationType fromCode(String code) {
        for (IdentificationType type : IdentificationType.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}