package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.application.response.company.TitleCompanyEmbeddedResponse;
import com.kynsoft.propertyacqcenter.domain.dto.BusinessDto;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyDto;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyTypeDto;
import com.kynsoft.propertyacqcenter.domain.dto.SubCompanyTypeDto;
import com.kynsoft.propertyacqcenter.domain.dto.embedded.company.LegalInformationDto;
import com.kynsoft.propertyacqcenter.domain.dto.embedded.company.SellerDto;
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
    private String notes;//
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;//
    private UUID updatedBy;//
    private String category;
    private SubCategoryResponse subCategory;
    private TitleCompanyEmbeddedResponse titleCompany;
    private SellerDto seller;
    private LegalInformationDto legalInformation;

    public CompanyResponse(CompanyDto dto) {
        this.id = dto.getId();
        this.category = dto.getCategory();
        this.subCategory = dto.getSubCategory() != null ? new SubCategoryResponse(dto.getSubCategory()) : null;
        this.companyType = dto.getCompanyType();
        this.business = dto.getBusiness();
        this.subCompanyType = dto.getSubCompanyType();
        this.titleCompany = dto.getTitleCompany() != null ? new TitleCompanyEmbeddedResponse(dto.getTitleCompany()) : null;
        this.title = dto.getTitle();
        this.notes = dto.getNotes();
        this.seller = dto.getSeller();
        this.legalInformation = dto.getLegalInformation();
        this.createdAt = dto.getCreatedAt();
        this.updatedAt = dto.getUpdatedAt();
        this.createdBy = dto.getCreatedBy();
        this.updatedBy = dto.getUpdatedBy();
    }
}
