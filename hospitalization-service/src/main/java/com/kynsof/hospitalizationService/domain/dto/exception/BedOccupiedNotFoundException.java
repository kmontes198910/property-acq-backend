package com.kynsof.hospitalizationService.domain.dto.exception;

import java.util.UUID;

public class BedOccupiedNotFoundException extends RuntimeException {
    public BedOccupiedNotFoundException(UUID id) {
        super("Bed is occupied.");
    }
}
