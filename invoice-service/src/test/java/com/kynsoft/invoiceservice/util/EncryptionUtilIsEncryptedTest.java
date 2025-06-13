package com.kynsoft.invoiceservice.util;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EncryptionUtilIsEncryptedTest {

    @Mock
    private Environment env;

    @Test
    public void testIsEncrypted_whenNull_returnsFalse() {
        // Arrange
        EncryptionUtil encryptionUtil = new EncryptionUtil("test-key-test-key-test-key-test-key", "0123456789abcdef", env);
        
        // Act
        boolean result = encryptionUtil.isEncrypted(null);
        
        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsEncrypted_whenEmpty_returnsFalse() {
        // Arrange
        EncryptionUtil encryptionUtil = new EncryptionUtil("test-key-test-key-test-key-test-key", "0123456789abcdef", env);
        
        // Act
        boolean result = encryptionUtil.isEncrypted("");
        
        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsEncrypted_whenPlainText_returnsFalse() {
        // Arrange
        EncryptionUtil encryptionUtil = new EncryptionUtil("test-key-test-key-test-key-test-key", "0123456789abcdef", env);
        
        // Act
        boolean result = encryptionUtil.isEncrypted("Esto es un texto sin encriptar");
        
        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsEncrypted_whenEncrypted_returnsTrue() {
        // Arrange
        EncryptionUtil encryptionUtil = new EncryptionUtil("test-key-test-key-test-key-test-key", "0123456789abcdef", env);
        String plainText = "texto-a-encriptar";
        String encrypted = encryptionUtil.encrypt(plainText);
        
        // Act
        boolean result = encryptionUtil.isEncrypted(encrypted);
        
        // Assert
        assertTrue(result);
    }
}
