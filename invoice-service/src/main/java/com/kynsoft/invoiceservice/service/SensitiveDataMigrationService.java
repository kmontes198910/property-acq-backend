//package com.kynsoft.invoiceservice.service;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.kynsoft.invoiceservice.infrastructure.entities.Issuer;
//import com.kynsoft.invoiceservice.infrastructure.repository.command.InvoiceIssuerWriteRepository;
//import com.kynsoft.invoiceservice.infrastructure.repository.query.InvoiceIssuerRepository;
//import com.kynsoft.invoiceservice.util.EncryptionUtil;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
///**
// * Servicio para migrar las credenciales existentes a formato encriptado
// * Este servicio se ejecutará automáticamente al iniciar la aplicación
// * para encriptar tanto las contraseñas como los certificados digitales
// */
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class SensitiveDataMigrationService {
//
//    private final InvoiceIssuerRepository issuerRepository;
//    private final InvoiceIssuerWriteRepository issuerWriteRepository;
//    private final EncryptionUtil encryptionUtil;
//
//    /**
//     * Método que se ejecuta tras la inicialización de la aplicación
//     * para migrar las credenciales no encriptadas
//     */
//    @EventListener(ApplicationReadyEvent.class)
//    @Transactional
//    public void migrateCredentials() {
//        log.info("Iniciando migración de credenciales de emisores");
//
//        // Obtener todos los emisores
//        List<Issuer> issuers = issuerRepository.findAll();
//        int migratedCount = 0;
//
//        for (Issuer issuer : issuers) {
//            boolean needsMigration = false;
//
//            try {
//                // Verificar si la contraseña necesita migración
//                String password = issuer.getDigitalCertPassword();
//                if (password != null && !password.isEmpty() && !encryptionUtil.isEncrypted(password)) {
//                    log.debug("La contraseña del emisor {} requiere encriptación", issuer.getId());
//                    needsMigration = true;
//                }
//
//                // Verificar si el certificado digital necesita migración
//                String certP12 = issuer.getDigitalCertP12();
//                if (certP12 != null && !certP12.isEmpty() && !encryptionUtil.isEncrypted(certP12)) {
//                    log.debug("El certificado digital del emisor {} requiere encriptación", issuer.getId());
//                    needsMigration = true;
//                }
//
//                // Si algún dato necesita migración, guardar la entidad para que el convertidor lo encripte
//                if (needsMigration) {
//                    log.info("Migrando credenciales del emisor {}", issuer.getId());
//                    // Encriptar manualmente para tener más control sobre el proceso
//                    if (password != null && !password.isEmpty() && !encryptionUtil.isEncrypted(password)) {
//                        String encryptedPassword = encryptionUtil.encrypt(password);
//                        issuer.setDigitalCertPassword(encryptedPassword);
//                    }
//
//                    if (certP12 != null && !certP12.isEmpty() && !encryptionUtil.isEncrypted(certP12)) {
//                        String encryptedCert = encryptionUtil.encrypt(certP12);
//                        issuer.setDigitalCertP12(encryptedCert);
//                    }
//
//                    issuerWriteRepository.save(issuer);
//                    migratedCount++;
//                }
//            } catch (Exception e) {
//                log.error("Error al migrar credenciales del emisor {}: {}", issuer.getId(), e.getMessage());
//                // Continuar con el siguiente emisor
//            }
//        }
//
//        log.info("Migración de credenciales completada: {} emisores actualizados", migratedCount);
//    }
//}
