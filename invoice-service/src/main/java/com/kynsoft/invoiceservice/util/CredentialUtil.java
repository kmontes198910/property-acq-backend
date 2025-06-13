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
        
        try {
            // Intentar desencriptar directamente
            // Si la operación es exitosa y el resultado es diferente, estaba encriptado
            String decrypted = encryptionUtil.decrypt(text);
            if (!text.equals(decrypted)) {
                log.debug("Texto desencriptado correctamente");
                return decrypted;
            }
        } catch (Exception e) {
            // Si falla la desencriptación, asumimos que no estaba encriptado
            log.debug("El texto no estaba encriptado o no se pudo desencriptar");
        }
        
        // Devolver el texto original si no se pudo desencriptar o no era necesario
        return text;
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
        
        try {
            // Intentamos desencriptar el texto como verificación 
            // Si se puede desencriptar exitosamente, ya está encriptado
            String decrypted = encryptionUtil.decrypt(text);
            if (!text.equals(decrypted)) {
                // Ya está encriptado, devolver el texto original
                return text;
            }
        } catch (Exception e) {
            // Si falla la desencriptación, probablemente no esté encriptado
            // Vamos a continuar y encriptarlo
        }
        
        // Encriptar el texto y devolverlo
        try {
            return encryptionUtil.encrypt(text);
        } catch (Exception e) {
            log.error("Error al encriptar texto sensible: {}", e.getMessage());
            throw new RuntimeException("No se pudo encriptar el texto sensible", e);
        }
    }
}
