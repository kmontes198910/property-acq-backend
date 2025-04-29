package com.kynsoft.cirugia.application.response;

public record ErrorResponse(
    int status,
    String error,
    String message,
    String timestamp
) {}