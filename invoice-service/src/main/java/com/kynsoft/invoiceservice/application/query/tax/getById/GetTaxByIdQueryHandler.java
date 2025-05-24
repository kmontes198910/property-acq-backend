package com.kynsoft.invoiceservice.application.query.tax.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.invoiceservice.domain.dto.TaxDto;
import com.kynsoft.invoiceservice.domain.service.ITaxService;
import com.kynsoft.invoiceservice.infrastructure.entities.Tax;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

/**
 * Manejador para la consulta de un impuesto por ID
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class GetTaxByIdQueryHandler implements IQueryHandler<GetTaxByIdQuery, TaxResponse> {

    private final ITaxService taxService;

    @Override
    public TaxResponse handle(GetTaxByIdQuery query) {
        log.info("Obteniendo impuesto con ID: {}", query.getId());
        
        // Buscar el impuesto por ID
        Tax tax = taxService.findById(query.getId())
                .orElseThrow(() -> new NoSuchElementException("Impuesto no encontrado con ID: " + query.getId()));
        
        // Crear el DTO utilizando el método fromEntity
        TaxDto taxDto = TaxDto.fromEntity(tax);
        
        // Crear y devolver la respuesta
        return TaxResponse.fromDto(taxDto);
    }
    
    // Ya no es necesario este método, utilizamos directamente TaxDto.fromEntity
}
