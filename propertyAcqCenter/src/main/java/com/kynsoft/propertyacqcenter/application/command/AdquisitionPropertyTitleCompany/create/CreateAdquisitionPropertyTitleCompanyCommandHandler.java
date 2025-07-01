package com.kynsoft.propertyacqcenter.application.command.AdquisitionPropertyTitleCompany.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.AdquisitionPropertyDto;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyContactDto;
import com.kynsoft.propertyacqcenter.domain.dto.GeneralDocumentDto;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.dto.embedded.adquisitionProperty.AdquisitionTitleCompanyDto;
import com.kynsoft.propertyacqcenter.domain.services.IAdquisitionPropertyService;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyContactService;
import com.kynsoft.propertyacqcenter.domain.services.IDocumentTypeService;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CreateAdquisitionPropertyTitleCompanyCommandHandler implements ICommandHandler<CreateAdquisitionPropertyTitleCompanyCommand> {

    private final IAdquisitionPropertyService adquisitionPropertyService;
    private final ILegalEntityService legalEntityService;
    private final IPropertyService propertyService;
    private final ICompanyContactService companyContactService;
    private final IDocumentTypeService documentTypeService;

    public CreateAdquisitionPropertyTitleCompanyCommandHandler(@Qualifier("adquisitionPropertyTitleCompany") IAdquisitionPropertyService adquisitionPropertyService, 
                                                   ILegalEntityService legalEntityService,
                                                   IPropertyService propertyService,
                                                   ICompanyContactService companyContactService,
                                                   IDocumentTypeService documentTypeService) {
        this.adquisitionPropertyService = adquisitionPropertyService;
        this.legalEntityService = legalEntityService;
        this.propertyService = propertyService;
        this.companyContactService = companyContactService;
        this.documentTypeService = documentTypeService;
    }

    @Override
    public void handle(CreateAdquisitionPropertyTitleCompanyCommand command) {
        LegalEntityDto legalEntityDto = command.getBuyer() != null ? this.legalEntityService.findById(command.getBuyer()) : null;
        PropertyDto propertyDto = command.getProperty() != null ? this.propertyService.getById(command.getProperty()) : null;
        CompanyContactDto contactDto = command.getContact() != null ? this.companyContactService.findById(command.getContact()) : null;

        adquisitionPropertyService.create(AdquisitionPropertyDto
                .builder()
                .id(command.getId())
                .buyer(legalEntityDto)
                .property(propertyDto)
                .contact(contactDto)
                .titleCompany(AdquisitionTitleCompanyDto
                        .builder()
                        .titleCommitment(command.getTitleCommitment())
                        .build())

                .build()
        );
    }

    private List<GeneralDocumentDto> generalDocuments(CreateAdquisitionPropertyTitleCompanyCommand command){
        List<GeneralDocumentDto> values = new ArrayList<>();
        if (command.getDocuments() != null) {
            command.getDocuments().forEach(x -> {
                values.add(GeneralDocumentDto.builder()
                        .id(UUID.randomUUID())
                        .documentType(this.documentTypeService.findById(x.getDocumentType()))
                        .fileName(x.getFileName())
                        .filePath(x.getFilePath())
                        .build()
                );
            });
        }
        return values;
    }

}
