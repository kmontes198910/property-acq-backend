package com.kynsoft.cirugia.domain.enums;

import lombok.Getter;

/**
 * Enumerado que define los posibles estados de un quirófano.
 */
@Getter
public enum SurgeryStatus {
    SCHEDULED,
    PRE_OPERATIVE,
    INTRA_OPERATIVE,
    POST_OPERATIVE,
    ROOM,
    DISCHARGED,
    CANCELLED,
    TERMINATED
}