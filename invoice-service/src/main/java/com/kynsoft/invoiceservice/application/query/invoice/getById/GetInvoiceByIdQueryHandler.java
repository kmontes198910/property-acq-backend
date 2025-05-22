package com.kynsoft.invoiceservice.application.query.invoice.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsoft.invoiceservice.domain.dto.InvoiceDto;
import com.kynsoft.invoiceservice.domain.service.IInvoiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Handler para procesar la consulta de factura por ID
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class GetInvoiceByIdQueryHandler implements IQueryHandler<GetInvoiceByIdQuery, InvoiceResponse> {

    private final IInvoiceService invoiceService;

    @Override
    @Transactional(readOnly = true)
    public InvoiceResponse handle(GetInvoiceByIdQuery query) {
        log.info("Buscando factura con ID: {}", query.getId());
        
        // Obtener los datos de la factura del servicio de dominio
        InvoiceDto invoice = invoiceService.findById(query.getId());
        
        if (invoice == null) {
            throw new BusinessNotFoundException(
                new GlobalBusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND, 
                new ErrorField("id", "Factura no encontrada con ID: " + query.getId())));
        }
        
        // Construir y devolver la respuesta con los datos de la factura
        return mapToResponse(invoice);
    }
    
    /**
     * Mapea los datos del DTO de dominio al DTO de respuesta
     */
    private InvoiceResponse mapToResponse(InvoiceDto invoice) {
        return InvoiceResponse.builder()
                .id(invoice.getId())
                .documentNumber(invoice.getDocumentNumber())
                .accessKey(invoice.getAccessKey())
                .issueDate(invoice.getEmissionDate())     // Mapeando emissionDate a issueDate
                .sequential(invoice.getSequential())
                
                // Detalles financieros
                .subtotal(invoice.getSubtotal())
                .discount(invoice.getDiscount())
                .taxAmount(invoice.getTaxAmount())
                .total(invoice.getTotalAmount())          // Mapeando totalAmount a total
                
                // Estado
                .status(invoice.getStatus())
                .createdAt(invoice.getCreatedAt())
                .updatedAt(invoice.getUpdatedAt())
                .createdBy(invoice.getCreatedBy())
                .updatedBy(invoice.getUpdatedBy())
                
                // Información adicional
                .details(invoice.getDetails())
                .taxes(invoice.getTaxes())
                .payments(invoice.getPayments())
                .additionalFields(invoice.getAdditionalFields())
                .build();
    }
}
