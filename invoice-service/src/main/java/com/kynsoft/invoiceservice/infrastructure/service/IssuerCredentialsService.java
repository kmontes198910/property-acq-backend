package com.kynsoft.invoiceservice.infrastructure.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kynsoft.invoiceservice.infrastructure.entities.Issuer;
import com.kynsoft.invoiceservice.infrastructure.repository.command.InvoiceIssuerWriteRepository;
import com.kynsoft.invoiceservice.infrastructure.repository.query.InvoiceIssuerRepository;
import com.kynsoft.invoiceservice.infrastructure.util.EncryptionUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.UUID;

/**
 * Servicio para gestionar operaciones específicas relacionadas con las credenciales del emisor
 * Incluye funciones para manejar tanto el certificado digital como su contraseña
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class IssuerCredentialsService {

    private final InvoiceIssuerRepository issuerRepository;
    private final InvoiceIssuerWriteRepository issuerWriteRepository;
    private final EncryptionUtil encryptionUtil;

    /**
     * Actualiza la contraseña del certificado digital para un emisor específico
     * 
     * @param issuerId ID del emisor
     * @param newPassword Nueva contraseña
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    @Transactional
    public boolean updatePassword(UUID issuerId, String newPassword) {
        log.info("Actualizando contraseña del certificado digital para el emisor: {}", issuerId);
        
        Optional<Issuer> issuerOpt = issuerRepository.findById(issuerId);
        if (issuerOpt.isEmpty()) {
            log.warn("No se encontró emisor con ID: {}", issuerId);
            return false;
        }
        
        Issuer issuer = issuerOpt.get();
        issuer.setDigitalCertPassword(newPassword);
        issuerWriteRepository.save(issuer);
        
        log.info("Contraseña del certificado digital actualizada exitosamente para el emisor: {}", issuerId);
        return true;
    }
    
    /**
     * Verifica si una contraseña es válida para un certificado específico
     * 
     * @param issuerId ID del emisor
     * @param password Contraseña para verificar
     * @return true si la contraseña es correcta, false en caso contrario
     */
    public boolean verifyPassword(UUID issuerId, String password) {
        log.info("Verificando contraseña del certificado digital para el emisor: {}", issuerId);
        
        Optional<Issuer> issuerOpt = issuerRepository.findById(issuerId);
        if (issuerOpt.isEmpty()) {
            log.warn("No se encontró emisor con ID: {}", issuerId);
            return false;
        }
        
        Issuer issuer = issuerOpt.get();
        String storedPassword = issuer.getDigitalCertPassword();
        
        // Si alguna de las contraseñas es nula, no pueden ser iguales
        if (storedPassword == null || password == null) {
            return false;
        }
        
        return storedPassword.equals(password);
    }
    
    /**
     * Actualiza el certificado digital para un emisor específico
     * 
     * @param issuerId ID del emisor
     * @param certificateData Datos del certificado en base64
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    @Transactional
    public boolean updateDigitalCertificate(UUID issuerId, String certificateData) {
        log.info("Actualizando certificado digital para el emisor: {}", issuerId);
        
        Optional<Issuer> issuerOpt = issuerRepository.findById(issuerId);
        if (issuerOpt.isEmpty()) {
            log.warn("No se encontró emisor con ID: {}", issuerId);
            return false;
        }
        
        Issuer issuer = issuerOpt.get();
        issuer.setDigitalCertP12(certificateData);
        issuerWriteRepository.save(issuer);
        
        log.info("Certificado digital actualizado exitosamente para el emisor: {}", issuerId);
        return true;
    }
    
    /**
     * Obtiene los datos del certificado digital de un emisor
     * 
     * @param issuerId ID del emisor
     * @return String con los datos del certificado, o null si no existe
     */
    public String getDigitalCertificate(UUID issuerId) {
        log.info("Obteniendo certificado digital para el emisor: {}", issuerId);
        
        Optional<Issuer> issuerOpt = issuerRepository.findById(issuerId);
        if (issuerOpt.isEmpty()) {
            log.warn("No se encontró emisor con ID: {}", issuerId);
            return null;
        }
        
        return issuerOpt.get().getDigitalCertP12();
    }
}
