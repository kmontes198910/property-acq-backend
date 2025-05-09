package com.kynsoft.finamer.digitalsignature.application.query.search;

import com.kynsof.share.core.domain.bus.query.IQuery;
import com.kynsof.share.core.domain.request.FilterCriteria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchDigitalSignatureCertificateQuery implements IQuery {
    private Pageable pageable;
    private List<FilterCriteria> filter;
    private String query;
    private UUID businessId;
    private UUID userId;
}