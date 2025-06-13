package com.kynsoft.invoiceservice.infrastructure.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kynsoft.invoiceservice.util.EncryptionUtil;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Convertidor JPA que encripta y desencripta automáticamente
 * los campos anotados con @Convert. Maneja tanto datos pequeños (como contraseñas)
 * como datos de gran tamaño (como certificados digitales P12)
 */
@Converter
@Component
public class AttributeEncryptor implements AttributeConverter<String, String> {

    private static EncryptionUtil encryptionUtil;
    
    /**
     * Constructor sin argumentos requerido por Hibernate
     */
    public AttributeEncryptor() {
        // Constructor vacío requerido por JPA/Hibernate
    }
    
    /**
     * Método setter que será usado por Spring para establecer la instancia de EncryptionUtil
     */
    @Autowired
    public void setEncryptionUtil(EncryptionUtil encryptionUtil) {
        AttributeEncryptor.encryptionUtil = encryptionUtil;
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            if (encryptionUtil == null) {
                throw new IllegalStateException("El objeto EncryptionUtil no ha sido inicializado correctamente");
            }
            return encryptionUtil.encrypt(attribute);
        } catch (Exception e) {
            throw new RuntimeException("Error al encriptar datos para almacenamiento en BD", e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        
        if (encryptionUtil == null) {
            throw new IllegalStateException("El objeto EncryptionUtil no ha sido inicializado correctamente");
        }
        
        try {
            // Intentar desencriptar directamente
            return encryptionUtil.decrypt(dbData);
        } catch (Exception e) {
            // Si falla la desencriptación, significa que los datos no estaban encriptados
            // o tienen un formato incorrecto. En cualquier caso, devolver los datos originales
            // para evitar errores durante la carga de datos
            return dbData;
        }
    }
}
