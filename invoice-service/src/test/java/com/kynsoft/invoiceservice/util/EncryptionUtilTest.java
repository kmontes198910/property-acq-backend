package com.kynsoft.invoiceservice.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "encryption.secret-key=TestSecretKeyForUnitTestingPurposes01",
    "encryption.salt=0123456789abcdef"
})
class EncryptionUtilTest {

    @Autowired
    private EncryptionUtil encryptionUtil;

    @Test
    void testEncryptAndDecrypt() {
        // Arrange
        String originalText = "password123";
        
        // Act
        String encryptedText = encryptionUtil.encrypt(originalText);
        String decryptedText = encryptionUtil.decrypt(encryptedText);
        
        // Assert
        assertNotEquals(originalText, encryptedText, "El texto encriptado debe ser diferente al original");
        assertEquals(originalText, decryptedText, "El texto desencriptado debe ser igual al original");
    }

    @Test
    void testEncryptTwice_ShouldReturnDifferentResults() {
        // Arrange
        String originalText = "password123";
        
        // Act
        String firstEncryption = encryptionUtil.encrypt(originalText);
        String secondEncryption = encryptionUtil.encrypt(originalText);
        
        // Assert - La encriptación debe usar un IV aleatorio y producir diferentes resultados cada vez
        assertNotEquals(firstEncryption, secondEncryption, 
                "Encriptaciones múltiples del mismo texto deben producir diferentes resultados");
    }
    
    @Test
    void testNullHandling() {
        // Act & Assert
        assertNull(encryptionUtil.encrypt(null), "Encriptar null debe retornar null");
        assertNull(encryptionUtil.decrypt(null), "Desencriptar null debe retornar null");
    }
    
    @Test
    void testEncryptAndDecryptLargeContent() {
        // Arrange - Simular un certificado digital P12 (contenido base64 ficticio)
        String mockP12Certificate = generateMockP12Base64Content(2048);
        
        // Act
        String encryptedCert = encryptionUtil.encrypt(mockP12Certificate);
        String decryptedCert = encryptionUtil.decrypt(encryptedCert);
        
        // Assert
        assertNotEquals(mockP12Certificate, encryptedCert, "El certificado encriptado debe ser diferente al original");
        assertEquals(mockP12Certificate, decryptedCert, "El certificado desencriptado debe ser igual al original");
    }
    
    @Test
    void testIsEncryptedMethod() {
        // Arrange
        String plainText = "textoPlano123";
        String encryptedText = encryptionUtil.encrypt(plainText);
        
        // Act & Assert
        assertFalse(encryptionUtil.isEncrypted(plainText), "El texto plano no debería detectarse como encriptado");
        assertTrue(encryptionUtil.isEncrypted(encryptedText), "El texto encriptado debería detectarse como encriptado");
    }
    
    /**
     * Genera un contenido base64 simulado para testear con tamaños similares a un certificado P12
     */
    private String generateMockP12Base64Content(int size) {
        byte[] randomBytes = new byte[size];
        for (int i = 0; i < size; i++) {
            randomBytes[i] = (byte) (Math.random() * 256);
        }
        return Base64.getEncoder().encodeToString(randomBytes);
    }
}
