package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.AdquisitionPropertyDto;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyContactDto;
import com.kynsoft.propertyacqcenter.domain.dto.GeneralDocumentDto;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.services.IAdquisitionPropertyService;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyContactService;
import com.kynsoft.propertyacqcenter.domain.services.IDocumentTypeService;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class CreateAdquisitionPropertyCommandHandler implements ICommandHandler<CreateAdquisitionPropertyCommand> {

    private final IAdquisitionPropertyService adquisitionPropertyService;
    private final ILegalEntityService legalEntityService;
    private final IPropertyService propertyService;
    private final ICompanyContactService companyContactService;
    private final IDocumentTypeService documentTypeService;

    public CreateAdquisitionPropertyCommandHandler(IAdquisitionPropertyService adquisitionPropertyService, 
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
    public void handle(CreateAdquisitionPropertyCommand command) {
        LegalEntityDto legalEntityDto = command.getBuyer() != null ? this.legalEntityService.findById(command.getBuyer()) : null;
        PropertyDto propertyDto = command.getProperty() != null ? this.propertyService.getById(command.getProperty()) : null;
        CompanyContactDto contactDto = command.getContact() != null ? this.companyContactService.findById(command.getContact()) : null;

        adquisitionPropertyService.create(AdquisitionPropertyDto
                .builder()
                .id(command.getId())
                .buyer(legalEntityDto)
                .property(propertyDto)
                .contact(contactDto)
                .buyerNameAndYearVehicle(command.getBuyerNameAndYearVehicle())
                .buyerLicenseTagNo(command.getBuyerLicenseTagNo())
                .dateAndTimeForInspections(command.getDateAndTimeForInspections())
                .instructionsForAccess(command.getInstructionsForAccess())
                .hoaBuyerInterviewDate(command.getHoaBuyerInterviewDate())
                .preferredMoveinDate(command.getPreferredMoveinDate())
                .eSignAuthorization(command.getESignAuthorization())
                .finalWalkthroughDate(command.getFinalWalkthroughDate())
                .wireAccountHolderName(command.getWireAccountHolderName())
                .wireAccountNumber(command.getWireAccountNumber())
                .wireRoutingNumber(command.getWireRoutingNumber())
                .zelleEmailorPhone(command.getZelleEmailorPhone())
                .electricProviderConfirmation(command.getElectricProviderConfirmation())
                .gasServiceConfirmation(command.getGasServiceConfirmation())
                .trashServiceConfirmation(command.getTrashServiceConfirmation())
                .waterSewerSetupConfirmation(command.getWaterSewerSetupConfirmation())

                .documents(generalDocuments(command))
                .build()
        );
    }

    private List<GeneralDocumentDto> generalDocuments(CreateAdquisitionPropertyCommand command){
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
