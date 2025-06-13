package com.kynsoft.invoiceservice.util;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CredentialUtilTest {

    @Mock
    private EncryptionUtil encryptionUtil;
    
    @InjectMocks
    private CredentialUtil credentialUtil;
    
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testEnsureDecrypted_WithNull() {
        // Arrange
        String nullValue = null;
        
        // Act
        String result = credentialUtil.ensureDecrypted(nullValue);
        
        // Assert
        assertNull(result);
        verify(encryptionUtil, never()).isEncrypted(any());
        verify(encryptionUtil, never()).decrypt(any());
    }
    
    @Test
    public void testEnsureDecrypted_WithEncryptedValue() {
        // Arrange
        String encryptedValue = "encryptedData123";
        String decryptedValue = "secretPassword";
        
        when(encryptionUtil.isEncrypted(encryptedValue)).thenReturn(true);
        when(encryptionUtil.decrypt(encryptedValue)).thenReturn(decryptedValue);
        
        // Act
        String result = credentialUtil.ensureDecrypted(encryptedValue);
        
        // Assert
        assertEquals(decryptedValue, result);
        verify(encryptionUtil).isEncrypted(encryptedValue);
        verify(encryptionUtil).decrypt(encryptedValue);
    }
    
    @Test
    public void testEnsureDecrypted_WithPlainValue() {
        // Arrange
        String plainValue = "plainPassword";
        
        when(encryptionUtil.isEncrypted(plainValue)).thenReturn(false);
        
        // Act
        String result = credentialUtil.ensureDecrypted(plainValue);
        
        // Assert
        assertEquals(plainValue, result);
        verify(encryptionUtil).isEncrypted(plainValue);
        verify(encryptionUtil, never()).decrypt(any());
    }
    
    @Test
    public void testEnsureDecrypted_WithError() {
        // Arrange
        String problematicValue = "problematic-data";
        
        when(encryptionUtil.isEncrypted(problematicValue)).thenReturn(true);
        when(encryptionUtil.decrypt(problematicValue)).thenThrow(new RuntimeException("Error al desencriptar"));
        
        // Act
        String result = credentialUtil.ensureDecrypted(problematicValue);
        
        // Assert
        assertEquals(problematicValue, result);  // Debería devolver el valor original en caso de error
        verify(encryptionUtil).isEncrypted(problematicValue);
        verify(encryptionUtil).decrypt(problematicValue);
    }
    
    @Test
    public void testEnsureEncrypted_WithNull() {
        // Arrange
        String nullValue = null;
        
        // Act
        String result = credentialUtil.ensureEncrypted(nullValue);
        
        // Assert
        assertNull(result);
        verify(encryptionUtil, never()).isEncrypted(any());
        verify(encryptionUtil, never()).encrypt(any());
    }
    
    @Test
    public void testEnsureEncrypted_WithEncryptedValue() {
        // Arrange
        String alreadyEncryptedValue = "encryptedData123";
        
        when(encryptionUtil.isEncrypted(alreadyEncryptedValue)).thenReturn(true);
        
        // Act
        String result = credentialUtil.ensureEncrypted(alreadyEncryptedValue);
        
        // Assert
        assertEquals(alreadyEncryptedValue, result);
        verify(encryptionUtil).isEncrypted(alreadyEncryptedValue);
        verify(encryptionUtil, never()).encrypt(any());
    }
    
    @Test
    public void testEnsureEncrypted_WithPlainValue() {
        // Arrange
        String plainValue = "plainPassword";
        String encryptedValue = "encryptedData123";
        
        when(encryptionUtil.isEncrypted(plainValue)).thenReturn(false);
        when(encryptionUtil.encrypt(plainValue)).thenReturn(encryptedValue);
        
        // Act
        String result = credentialUtil.ensureEncrypted(plainValue);
        
        // Assert
        assertEquals(encryptedValue, result);
        verify(encryptionUtil).isEncrypted(plainValue);
        verify(encryptionUtil).encrypt(plainValue);
    }
    
    @Test
    public void testEnsureEncrypted_WithError() {
        // Arrange
        String problematicValue = "problematic-data";
        
        when(encryptionUtil.isEncrypted(problematicValue)).thenReturn(false);
        when(encryptionUtil.encrypt(problematicValue)).thenThrow(new RuntimeException("Error al encriptar"));
        
        // Act & Assert
        assertThrows(RuntimeException.class, () -> credentialUtil.ensureEncrypted(problematicValue));
        verify(encryptionUtil).isEncrypted(problematicValue);
        verify(encryptionUtil).encrypt(problematicValue);
    }
}
