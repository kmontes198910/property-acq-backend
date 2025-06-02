package com.kynsoft.cirugia.domain.enums;

import lombok.Getter;

/**
 * Enumerado que define los posibles estados de un quirófano.
 */
@Getter
public enum OperatingRoomStatus {
    /**
     * El quirófano está disponible para su uso
     */
    AVAILABLE,
    
    /**
     * El quirófano está actualmente ocupado
     */
    OCCUPIED,
    
    /**
     * El quirófano está en mantenimiento
     */
    MAINTENANCE,
    
    /**
     * El quirófano está en proceso de limpieza
     */
    CLEANING,
    
    /**
     * El quirófano está reservado para un procedimiento futuro
     */
    RESERVED,
    
    /**
     * El quirófano está fuera de servicio
     */
    OUT_OF_SERVICE
    

    

}