package com.kynsoft.invoiceservice.application.query.invoiceIssuer.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsoft.invoiceservice.domain.dto.InvoiceIssuerDto;
import com.kynsoft.invoiceservice.domain.exception.BusinessInvoiceException;
import com.kynsoft.invoiceservice.domain.service.IInvoiceIssuerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetInvoiceIssuerByIdQueryHandler implements IQueryHandler<GetInvoiceIssuerByIdQuery, InvoiceIssuerResponse> {

    private final IInvoiceIssuerService invoiceIssuerService;

    @Override
    @Transactional(readOnly = true)
    public InvoiceIssuerResponse handle(GetInvoiceIssuerByIdQuery query) {
        log.info("Finding invoice issuer with ID: {}", query.getId());
        
        try {
            InvoiceIssuerDto issuerDto = invoiceIssuerService.getById(query.getId());
            return InvoiceIssuerResponse.fromDto(issuerDto);
        } catch (BusinessInvoiceException e) {
            throw new BusinessNotFoundException(
                new GlobalBusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND, 
                new ErrorField("id", "Invoice issuer not found with ID: " + query.getId())));
        }
    }
}
