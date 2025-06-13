package com.kynsoft.invoiceservice.infrastructure.converters;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.kynsoft.invoiceservice.util.EncryptionUtil;

public class AttributeEncryptorTest {

    private AttributeEncryptor encryptor;
    private EncryptionUtil encryptionUtil;
    
    @BeforeEach
    public void setup() {
        encryptor = new AttributeEncryptor();
        encryptionUtil = mock(EncryptionUtil.class);
        encryptor.setEncryptionUtil(encryptionUtil);
    }
    
    @Test
    public void testConvertToDatabaseColumn() {
        // Setup
        String rawValue = "mi-contraseña-secreta";
        String encryptedValue = "valorEncriptado123";
        when(encryptionUtil.encrypt(rawValue)).thenReturn(encryptedValue);
        
        // Test
        String result = encryptor.convertToDatabaseColumn(rawValue);
        
        // Verify
        assertEquals(encryptedValue, result);
        verify(encryptionUtil).encrypt(rawValue);
    }
    
    @Test
    public void testConvertToEntityAttribute_Encrypted() {
        // Setup
        String encryptedValue = "valorEncriptado123";
        String decryptedValue = "mi-contraseña-secreta";
        when(encryptionUtil.isEncrypted(encryptedValue)).thenReturn(true);
        when(encryptionUtil.decrypt(encryptedValue)).thenReturn(decryptedValue);
        
        // Test
        String result = encryptor.convertToEntityAttribute(encryptedValue);
        
        // Verify
        assertEquals(decryptedValue, result);
        verify(encryptionUtil).isEncrypted(encryptedValue);
        verify(encryptionUtil).decrypt(encryptedValue);
    }
    
    @Test
    public void testConvertToEntityAttribute_NotEncrypted() {
        // Setup
        String rawValue = "valor-sin-encriptar";
        when(encryptionUtil.isEncrypted(rawValue)).thenReturn(false);
        
        // Test
        String result = encryptor.convertToEntityAttribute(rawValue);
        
        // Verify
        assertEquals(rawValue, result);
        verify(encryptionUtil).isEncrypted(rawValue);
        verify(encryptionUtil, never()).decrypt(anyString());
    }
    
    @Test
    public void testConvertToEntityAttribute_Exception() {
        // Setup
        String problematicValue = "valor-problematico";
        when(encryptionUtil.isEncrypted(problematicValue)).thenReturn(true);
        when(encryptionUtil.decrypt(problematicValue)).thenThrow(new RuntimeException("Error simulado"));
        
        // Test
        String result = encryptor.convertToEntityAttribute(problematicValue);
        
        // Verify
        assertEquals(problematicValue, result);
        verify(encryptionUtil).isEncrypted(problematicValue);
        verify(encryptionUtil).decrypt(problematicValue);
    }
}
