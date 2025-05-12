package com.kynsoft.invoiceservice.application.command.invoice.generate;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.invoiceservice.application.command.invoice.generate.request.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter

@AllArgsConstructor
public class GenerateInvoiceCommand implements ICommand {
    // ID del emisor de la factura
    private UUID id;
    private String accessKey;
    private final UUID issuerId;
    // Información del comprador
    private final CustomerRequest customer;
    // Valores adicionales
    private final BigDecimal propina;
    // Detalles, pagos e información adicional
    private final List<DetalleFacturaRequest> detalles;
    private final List<PagoRequest> pagos;
    private final List<CampoAdicionalRequest> infoAdicional;

    private final UUID cratedBy;

    public GenerateInvoiceCommand(UUID issuerId, CustomerRequest customer, BigDecimal propina, List<DetalleFacturaRequest> detalles, List<PagoRequest> pagos, List<CampoAdicionalRequest> infoAdicional, UUID cratedBy) {
        this.issuerId = issuerId;
        this.customer = customer;
        this.propina = propina;
        this.detalles = detalles;
        this.pagos = pagos;
        this.infoAdicional = infoAdicional;
        this.cratedBy = cratedBy;
    }

    public static GenerateInvoiceCommand fromRequest(GenerateInvoiceRequest request, UUID createdBy) {
        return new GenerateInvoiceCommand(
                request.getIssuerId(),
                request.getCustomer(),
                request.getPropina(),
                request.getDetalles(),
                request.getPagos(),
                request.getInfoAdicional(),
                createdBy
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new GenerateInvoiceMessage(id, accessKey);
    }
}