package com.kynsoft.invoiceservice.domain.service;

import com.kynsoft.invoiceservice.domain.dto.MailJetAttachmentDto;
import com.kynsoft.invoiceservice.domain.dto.MailJetRecipientDto;
import com.kynsoft.invoiceservice.domain.dto.MailJetVarDto;
import com.kynsoft.invoiceservice.domain.dto.SendMailJetEmailRequestDto;
import com.kynsoft.invoiceservice.infrastructure.service.CloudBridgesFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Servicio para enviar notificaciones por correo electrónico relacionadas con facturas
 */
@Service
public class InvoiceEmailService {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceEmailService.class);
    
    private final CloudBridgesFileService cloudBridgesService;
    
    @Autowired
    public InvoiceEmailService(CloudBridgesFileService cloudBridgesService) {
        this.cloudBridgesService = cloudBridgesService;
    }
    
    /**
     * Envía una notificación de factura por correo electrónico
     *
     * @param invoiceNumber       Número de factura
     * @param invoiceAmount       Monto de la factura
     * @param recipientEmail      Email del destinatario
     * @param recipientName       Nombre del destinatario
     * @param attachmentPdfBase64 Contenido del adjunto en Base64 (opcional)
     * @return true si el correo se envió correctamente, false en caso contrario
     */
    public boolean sendInvoiceNotification(
            UUID invoiceId,
            String keyAccess,
            String invoiceNumber,
            BigDecimal invoiceAmount,
            String recipientEmail,
            String recipientName,
            String attachmentPdfBase64,
            String attachmentXmlBase64,
            String documentName) {
        
        logger.info("Preparando notificación de factura {} para enviar a {}", 
                invoiceNumber, recipientEmail);
        
        try {
            // Crear el objeto de solicitud
            SendMailJetEmailRequestDto requestDto = new SendMailJetEmailRequestDto();
            
            // Configurar destinatario
            List<MailJetRecipientDto> recipients = new ArrayList<>();
            recipients.add(new MailJetRecipientDto(recipientEmail, recipientName));
            requestDto.setRecipientEmail(recipients);
            
            // Configurar variables para la plantilla
            List<MailJetVarDto> vars = new ArrayList<>();
            vars.add(new MailJetVarDto("invoice_number", invoiceNumber));
            vars.add(new MailJetVarDto("invoice_amount", invoiceAmount));
            vars.add(new MailJetVarDto("client_name", recipientName));
            vars.add(new MailJetVarDto("key_access", keyAccess));
            vars.add(new MailJetVarDto("issue_date", new Date()));
            requestDto.setMailJetVars(vars);
            
            // Configurar el asunto
            requestDto.setSubject("Factura #" + invoiceNumber);
            
            // ID de la plantilla en Mailjet (este es un ejemplo, debe configurarse el ID correcto)
            requestDto.setTemplateId("12345678");
            
            // Añadir adjuntos si es necesario
            if (attachmentPdfBase64 != null && !attachmentPdfBase64.isEmpty()) {
                List<MailJetAttachmentDto> attachments = new ArrayList<>();
                attachments.add(new MailJetAttachmentDto(
                        "application/pdf",
                        documentName + ".pdf",
                        attachmentPdfBase64
                ));
                attachments.add(new MailJetAttachmentDto(
                        "application/xml",
                        documentName + ".xml",
                        attachmentXmlBase64
                ));
                requestDto.setMailJetAttachments(attachments);
            }
            
            // Enviar la solicitud
            ResponseEntity<?> response = cloudBridgesService.sendEmail(requestDto);
            
            logger.info("Notificación de factura {} enviada con estado: {}", 
                    invoiceNumber, response.getStatusCode());
            
            return response.getStatusCode().is2xxSuccessful();
            
        } catch (Exception e) {
            logger.error("Error al enviar notificación de factura {}: {}", 
                    invoiceNumber, e.getMessage(), e);
            return false;
        }
    }
}
