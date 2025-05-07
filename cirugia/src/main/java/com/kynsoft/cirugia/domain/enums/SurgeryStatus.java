package com.kynsoft.cirugia.domain.enums;

import lombok.Getter;

/**
 * Enumerado que define los posibles estados de un quirófano.
 */
@Getter
public enum SurgeryStatus {
    SCHEDULED,
    IN_PROGRESS,
    MAINTENANCE,
    CANCELLED,
    TERMINATED
}