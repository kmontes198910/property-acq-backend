package com.kynsoft.finamer.digitalsignature.domain.service;

import com.kynsoft.finamer.digitalsignature.domain.dto.*;

public interface IDigitalSignatureService {
    
    /**
     * Firma un documento PDF con una firma digital
     * @param request Datos de la solicitud de firma
     * @return Documento firmado y metadatos asociados
     */
    SignResponseDto signDocument(SignRequestDto request);
    
    /**
     * Valida las firmas en un documento PDF
     * @param request Datos de la solicitud de validación
     * @return Resultado de la validación y metadatos de firmas
     */
    ValidationResponseDto validateDocument(ValidationRequestDto request);
    
    /**
     * Verifica si la posición de firma especificada es válida
     * @param visibleSignature Detalles de la posición de firma visible
     * @param document Documento PDF en formato binario
     * @throws InvalidSignaturePositionException Si la posición es inválida
     */
    void validateSignaturePosition(VisibleSignatureDto visibleSignature, byte[] document);
}