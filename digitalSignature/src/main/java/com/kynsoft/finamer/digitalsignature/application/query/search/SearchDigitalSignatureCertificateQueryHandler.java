package com.kynsoft.finamer.digitalsignature.application.query.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.finamer.digitalsignature.domain.service.IDigitalSignatureCertificateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SearchDigitalSignatureCertificateQueryHandler implements IQueryHandler<SearchDigitalSignatureCertificateQuery, PaginatedResponse> {

    private final IDigitalSignatureCertificateService digitalSignatureCertificateService;

    @Override
    public PaginatedResponse handle(SearchDigitalSignatureCertificateQuery query) {
        log.info("Buscando firmas digitales con criterios de filtro y consulta: {}", query.getQuery());
        
       return digitalSignatureCertificateService.search(
                query.getPageable(), query.getFilter(), query.getQuery());

    }
}