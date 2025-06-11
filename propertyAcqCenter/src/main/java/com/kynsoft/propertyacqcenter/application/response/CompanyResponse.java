package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.BusinessDto;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyDto;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyTypeDto;
import com.kynsoft.propertyacqcenter.domain.dto.SubCompanyTypeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CompanyResponse implements IResponse {

    private UUID id;
    private BusinessDto business;
    private CompanyTypeDto companyType;
    private SubCompanyTypeDto subCompanyType;
    private String title;//
    private Double ownershipPercentage;//
    private Boolean signatureAuthority;//
    private String notes;//
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;//
    private UUID updatedBy;//
    private String category;
    private SubCategoryResponse subCategory;

    public CompanyResponse(CompanyDto dto) {
        this.id = dto.getId();
        this.category = dto.getCategory();
        this.subCategory = dto.getSubCategory() != null ? new SubCategoryResponse(dto.getSubCategory()) : null;
        this.companyType = dto.getCompanyType();
        this.business = dto.getBusiness();
        this.subCompanyType = dto.getSubCompanyType();
        this.title = dto.getTitle();
        this.ownershipPercentage = dto.getOwnershipPercentage();
        this.signatureAuthority = dto.getSignatureAuthority();
        this.notes = dto.getNotes();
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
    }
}
