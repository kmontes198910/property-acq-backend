
package com.kynsof.hospitalizationService.domain.exception.entity;

public record ErrorResponse(
    int status,
    String error,
    String message,
    String timestamp
) {}