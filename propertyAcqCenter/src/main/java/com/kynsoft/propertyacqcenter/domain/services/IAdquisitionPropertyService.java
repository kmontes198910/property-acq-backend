package com.kynsoft.propertyacqcenter.domain.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.dto.AdquisitionPropertyDocumentDto;
import com.kynsoft.propertyacqcenter.domain.dto.AdquisitionPropertyDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IAdquisitionPropertyService {
    UUID create(AdquisitionPropertyDto dto);
    
    void update(AdquisitionPropertyDto dto);
    
    void delete(UUID id);
    
    AdquisitionPropertyDto findById(UUID id);
    
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);

    List<AdquisitionPropertyDocumentDto> findByPropertyId(String propertyId);

    boolean existsByPropertyId(String propertyId);

    void updateBankStatementRequest(UUID id, String bankStatementRequest);

    void updatebuyerPersonalBankStatements(UUID id, String buyerPersonalBankStatements);

    void updateSellerPersonalBankStatements(UUID id, String sellerPersonalBankStatements);

    void updateSellerBankStatementRequest(UUID id, String sellerBankStatementRequest);
}