package com.kynsoft.invoiceservice.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Utilidad para encriptar y desencriptar datos sensibles
 */
@Component
@Slf4j
public class EncryptionUtil {

    private final TextEncryptor encryptor;

    /**
     * Constructor que inicializa el encriptador con la clave y sal configuradas.
     * Prioriza cargar las claves desde variables de entorno.
     * 
     * @param secretKey Clave secreta para encriptación desde propiedades
     * @param salt Sal para encriptación desde propiedades
     * @param env Entorno para acceder a variables de sistema
     */
    public EncryptionUtil(
            @Value("${encryption.secret-key:ZX1FeDoJGVEvr1oYDS648BmWyOV4mbeZ}") String secretKey,
            @Value("${encryption.salt:ae3bbcf756a6d8c9}") String salt,
            Environment env) {
        
        // Intentar cargar claves desde variables de entorno
        String envSecretKey = env.getProperty("ENCRYPTION_SECRET_KEY");
        String envSalt = env.getProperty("ENCRYPTION_SALT");
        
        // Usar variables de entorno si están disponibles
        String finalSecretKey = envSecretKey != null ? envSecretKey : secretKey;
        String finalSalt = envSalt != null ? envSalt : salt;
        
        // Registrar origen de claves (sin mostrar los valores)
        if (envSecretKey != null) {
            log.info("Usando clave de encriptación desde variables de entorno");
        } else {
            log.info("Usando clave de encriptación desde archivo de propiedades");
        }
        
        this.encryptor = Encryptors.text(finalSecretKey, finalSalt);
    }

    /**
     * Encripta un texto
     * 
     * @param text Texto a encriptar
     * @return Texto encriptado
     */
    public String encrypt(String text) {
        if (text == null) {
            return null;
        }
        
        try {
            return encryptor.encrypt(text);
        } catch (Exception e) {
            log.error("Error al encriptar texto: {}", e.getMessage());
            throw new RuntimeException("Error durante la encriptación", e);
        }
    }

    /**
     * Desencripta un texto
     * 
     * @param encryptedText Texto encriptado
     * @return Texto desencriptado
     */
    public String decrypt(String encryptedText) {
        if (encryptedText == null) {
            return null;
        }
        
        try {
            return encryptor.decrypt(encryptedText);
        } catch (Exception e) {
            log.error("Error al desencriptar texto: {}", e.getMessage());
            throw new RuntimeException("Error durante la desencriptación", e);
        }
    }
    
    /**
     * Verifica si un texto está encriptado comprobando su formato y estructura
     * 
     * @param text Texto a verificar
     * @return true si el texto está encriptado, false en caso contrario
     */
    public boolean isEncrypted(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        
        // Verificar patrón de Spring Security Crypto
        // El formato típico de datos encriptados con Spring Security es hexadecimal
        // pero también debe cumplir con un patrón específico
        if (!text.matches("^[0-9a-fA-F]+$")) {
            return false;
        }
        
        // Características adicionales para validar:
        // 1. Longitud mínima (los datos encriptados suelen ser más largos)
        if (text.length() < 32) {
            return false;
        }
        
        // Verificación definitiva: intentar desencriptar
        try {
            encryptor.decrypt(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
