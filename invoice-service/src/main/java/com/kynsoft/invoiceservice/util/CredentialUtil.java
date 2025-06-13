package com.kynsoft.invoiceservice.util;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Utilidad para manejar operaciones específicas relacionadas con las credenciales sensibles
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CredentialUtil {

    private final EncryptionUtil encryptionUtil;
    
    /**
     * Asegura que un texto sensible esté desencriptado para su uso
     * Si el texto ya está desencriptado, lo devuelve como está
     * Si está encriptado, lo desencripta primero
     * 
     * @param text Texto que podría estar encriptado
     * @return Texto desencriptado
     */
    public String ensureDecrypted(String text) {
        if (text == null) {
            return null;
        }
        
        // Verificar si el texto ya está encriptado
        if (encryptionUtil.isEncrypted(text)) {
            try {
                // Si está encriptado, desencriptarlo
                return encryptionUtil.decrypt(text);
            } catch (Exception e) {
                log.warn("Error al intentar desencriptar texto, devolviéndolo tal cual: {}", e.getMessage());
                return text;
            }
        } else {
            // Si no está encriptado, devolverlo como está
            return text;
        }
    }
    
    /**
     * Asegura que un texto sensible esté encriptado para almacenamiento
     * Si el texto ya está encriptado, lo devuelve como está
     * Si no está encriptado, lo encripta primero
     * 
     * @param text Texto que podría no estar encriptado
     * @return Texto encriptado
     */
    public String ensureEncrypted(String text) {
        if (text == null) {
            return null;
        }
        
        // Verificar si el texto ya está encriptado
        if (!encryptionUtil.isEncrypted(text)) {
            try {
                // Si no está encriptado, encriptarlo
                return encryptionUtil.encrypt(text);
            } catch (Exception e) {
                log.warn("Error al intentar encriptar texto: {}", e.getMessage());
                throw new RuntimeException("No se pudo encriptar el texto sensible", e);
            }
        } else {
            // Si ya está encriptado, devolverlo como está
            return text;
        }
    }
}
