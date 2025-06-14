package com.kynsoft.invoiceservice.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kynsoft.invoiceservice.application.command.Issuer.password.CertificateUpdateRequest;
import com.kynsoft.invoiceservice.application.command.Issuer.password.PasswordUpdateRequest;
import com.kynsoft.invoiceservice.application.command.Issuer.password.PasswordVerificationRequest;
import com.kynsoft.invoiceservice.infrastructure.service.IssuerCredentialsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controlador para gestionar operaciones relacionadas con las credenciales de los emisores
 */
@RestController
@RequestMapping("/api/v1/issuer-credentials")
@RequiredArgsConstructor
@Slf4j
public class IssuerCredentialsController {

    private final IssuerCredentialsService issuerCredentialsService;
    
    /**
     * Actualiza la contraseña del certificado digital de un emisor
     * 
     * @param issuerId ID del emisor
     * @param request Objeto con la nueva contraseña
     * @return Respuesta con el resultado de la operación
     */
    @PostMapping("/{issuerId}/update-password")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> updatePassword(
            @PathVariable UUID issuerId,
            @RequestBody PasswordUpdateRequest request) {
        
        log.info("Solicitud para actualizar contraseña del certificado digital para el emisor: {}", issuerId);
        
        boolean success = issuerCredentialsService.updatePassword(issuerId, request.getPassword());
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        
        if (success) {
            response.put("message", "La contraseña se actualizó correctamente");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "No se pudo actualizar la contraseña. Verifique que el emisor existe.");
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Verifica si una contraseña es válida para el certificado digital de un emisor
     * 
     * @param issuerId ID del emisor
     * @param request Objeto con la contraseña a verificar
     * @return Respuesta con el resultado de la verificación
     */
    @PostMapping("/{issuerId}/verify-password")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Map<String, Object>> verifyPassword(
            @PathVariable UUID issuerId,
            @RequestBody PasswordVerificationRequest request) {
        
        log.info("Solicitud para verificar contraseña del certificado digital para el emisor: {}", issuerId);
        
        boolean isValid = issuerCredentialsService.verifyPassword(issuerId, request.getPassword());
        
        Map<String, Object> response = new HashMap<>();
        response.put("valid", isValid);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Actualiza el certificado digital de un emisor
     * 
     * @param issuerId ID del emisor
     * @param request Objeto con los datos del certificado en base64
     * @return Respuesta con el resultado de la operación
     */
    @PostMapping("/{issuerId}/update-certificate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> updateCertificate(
            @PathVariable UUID issuerId,
            @RequestBody CertificateUpdateRequest request) {
        
        log.info("Solicitud para actualizar certificado digital para el emisor: {}", issuerId);
        
        boolean success = issuerCredentialsService.updateDigitalCertificate(issuerId, request.getCertificateData());
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        
        if (success) {
            response.put("message", "El certificado digital se actualizó correctamente");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "No se pudo actualizar el certificado. Verifique que el emisor existe.");
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Obtiene el certificado digital de un emisor
     * 
     * @param issuerId ID del emisor
     * @return Respuesta con los datos del certificado
     */
    @GetMapping("/{issuerId}/certificate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getCertificate(@PathVariable UUID issuerId) {
        
        log.info("Solicitud para obtener certificado digital del emisor: {}", issuerId);
        
        String certificateData = issuerCredentialsService.getDigitalCertificate(issuerId);
        
        Map<String, Object> response = new HashMap<>();
        
        if (certificateData != null) {
            response.put("success", true);
            response.put("certificateData", certificateData);
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "No se encontró el certificado digital para el emisor indicado");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
