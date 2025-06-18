package com.kynsoft.invoiceservice.domain.service;

import com.kynsoft.invoiceservice.infrastructure.service.CloudBridgesFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * Servicio de ejemplo que muestra cómo usar CloudBridgesFileService
 * para subir archivos relacionados con facturas
 */
@Service
public class InvoiceFileUploadService {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceFileUploadService.class);
    
    private final CloudBridgesFileService cloudBridgesFileService;

    @Autowired
    public InvoiceFileUploadService(CloudBridgesFileService cloudBridgesFileService) {
        this.cloudBridgesFileService = cloudBridgesFileService;
    }

    /**
     * Sube un archivo relacionado con una factura al servicio CloudBridges
     * 
     * @param file Archivo a subir
     * @param invoiceId ID de la factura
     * @param userId ID del usuario que realiza la acción
     * @param userName Nombre del usuario que realiza la acción
     * @param businessId ID del negocio asociado
     * @return Respuesta del servicio con la información del archivo subido
     */
    public ResponseEntity<?> uploadInvoiceFile(
            MultipartFile file,
            String invoiceId,
            String userId,
            String userName,
            UUID businessId) {
        
        logger.info("Subiendo archivo relacionado con la factura {}: {}", 
                invoiceId, file.getOriginalFilename());
        
        // Definimos la carpeta donde se guardará el archivo (por ejemplo: invoices/[invoiceId])
        String folderPath = "invoices/" + invoiceId;
        
        // Llamamos al servicio CloudBridges para subir el archivo
        ResponseEntity<?> response = cloudBridgesFileService.uploadFile(
                file,
                invoiceId,  // El ID de la factura como objectId
                folderPath,
                userId,
                userName,
                businessId
        );
        
        logger.info("Archivo de factura {} subido con resultado: {}", 
                invoiceId, response.getStatusCode());
        
        return response;
    }
    
    /**
     * Sube un archivo relacionado con una factura usando parámetros simplificados
     * 
     * @param file Archivo a subir
     * @param invoiceId ID de la factura
     * @return Respuesta del servicio con la información del archivo subido
     */
    public ResponseEntity<?> uploadInvoiceFile(MultipartFile file, String invoiceId) {
        logger.info("Subiendo archivo para factura {} (versión simplificada): {}", 
                invoiceId, file.getOriginalFilename());
        
        // Definimos la carpeta donde se guardará el archivo
        String folderPath = "invoices/" + invoiceId;
        
        // Llamamos al método simplificado
        return cloudBridgesFileService.uploadFile(file, invoiceId, folderPath);
    }
}
