package com.kynsoft.propertyacqcenter.domain.dto.exception.propertyImagen;

import java.util.UUID;

public class PropertyImagenMainMustBeUniqueException extends RuntimeException {
    public PropertyImagenMainMustBeUniqueException(String property) {
        super("The imagen main already exists.");
    }
}
