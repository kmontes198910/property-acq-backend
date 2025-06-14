package com.kynsoft.invoiceservice.application.command.invoice.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;

import com.kynsoft.invoiceservice.application.command.invoice.update.request.*;
import com.kynsoft.invoiceservice.infrastructure.entities.InvoiceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Comando para actualizar una factura existente
 */
@Data
@Builder
@AllArgsConstructor
public class UpdateInvoiceCommand implements ICommand{

    // ID del emisor de la factura
    private UUID id;
    private  UUID issuerId;
    private InvoiceStatus status;
    // Información del comprador
    private CustomerRequest customer;
    // Valores adicionales
    private  BigDecimal propina;
    // Detalles, pagos e información adicional
    private  List<DetalleFacturaRequest> detalles;
    private  List<PagoRequest> pagos;
    private  List<CampoAdicionalRequest> infoAdicional;

    private  UUID createdBy;
    private UUID updatedBy;
    private boolean result;
    
    /**
     * Crea un comando a partir de una solicitud de actualización
     *
     * @param request Solicitud de actualización
     * @param id ID de la factura a actualizar
     * @param userId ID del usuario que realiza la actualización
     * @return El comando creado
     */
    public static UpdateInvoiceCommand fromRequest(UpdateInvoiceRequest request, UUID id, UUID userId) {
        return UpdateInvoiceCommand.builder()
                .id(id)
                .issuerId(request.getIssuerId())
                .status(request.getStatus()) // Asignar un estado por defecto, puede ser modificado según la lógica
                .customer(request.getCustomer())
                .propina(request.getPropina())
                .detalles(request.getDetalles())
                .pagos(request.getPagos())
                .infoAdicional(request.getInfoAdicional())
                .createdBy(userId)
                .updatedBy(userId)
                .result(false) // Por defecto, el resultado es false
                .build();
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateInvoiceMessage(id, result, "Factura actualizada correctamente");
    }
}
