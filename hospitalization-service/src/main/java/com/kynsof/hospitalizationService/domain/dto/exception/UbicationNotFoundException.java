package com.kynsof.hospitalizationService.domain.dto.exception;

import java.util.UUID;

public class UbicationNotFoundException extends RuntimeException {
    public UbicationNotFoundException(UUID id) {
        super("Ubication with ID " + id + " not found");
    }
}
