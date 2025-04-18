package com.kynsoft.finamer.digitalsignature.api.handler;

import com.kynsoft.finamer.digitalsignature.domain.exception.InvalidSignaturePositionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(InvalidSignaturePositionException.class)
    public ResponseEntity<Object> handleInvalidSignaturePositionException(
            InvalidSignaturePositionException ex, WebRequest request) {
        
        logger.error("Error en posición de firma: {}", ex.getMessage());
        
        Map<String, Object> body = new HashMap<>();
        body.put("code", ex.getError().getCode());
        body.put("message", ex.getError().getMessage());
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Error-Code", ex.getError().getCode());
        
        return new ResponseEntity<>(body, headers, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        logger.error("Error no controlado: ", ex);
        
        Map<String, Object> body = new HashMap<>();
        body.put("code", "SIGN-999");
        body.put("message", "Error interno del servidor");
        
        return new ResponseEntity<>(body, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}