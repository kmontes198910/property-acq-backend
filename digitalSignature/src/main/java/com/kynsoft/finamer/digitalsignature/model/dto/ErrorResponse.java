package com.kynsoft.finamer.digitalsignature.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase para representar respuestas de error.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    
    /**
     * Código de error
     */
    private String code;
    
    /**
     * Mensaje de error
     */
    private String message;
    
    /**
     * Indica si la operación fue exitosa
     */
    private boolean success = false;
}
