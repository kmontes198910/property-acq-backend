package com.kynsoft.propertyacqcenter.application.command.propertyTeam.createAll;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyContactDto;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyTeamDto;
import com.kynsoft.propertyacqcenter.domain.enums.CompanyType;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyContactService;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyTeamService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreatePropertyTeamAllCommandHandler implements ICommandHandler<CreatePropertyTeamAllCommand> {

    private final IPropertyTeamService propertyTeamService;
    private final IPropertyService propertyService;
    private final ICompanyContactService companyContactService;
    private final ILegalEntityService legalEntityService;

    public CreatePropertyTeamAllCommandHandler(IPropertyTeamService propertyTeamService,
            IPropertyService propertyService, ICompanyContactService companyContactService,
            ILegalEntityService legalEntityService) {
        this.propertyTeamService = propertyTeamService;
        this.propertyService = propertyService;
        this.companyContactService = companyContactService;
        this.legalEntityService = legalEntityService;
    }

    @Override
    @Transactional
    public void handle(CreatePropertyTeamAllCommand command) {
        PropertyDto propertyDto = this.propertyService.getById(command.getProperty());
        LegalEntityDto buyerEntityName = command.getBuyerEntityName() != null ? this.legalEntityService.findById(command.getBuyerEntityName()) : null;
        List<PropertyTeamDto> list = new ArrayList<>();
        for (UUID buyer : command.getBuyerContactRep()) {
            CompanyContactDto contact = this.companyContactService.findById(buyer);
            list.add(PropertyTeamDto
                .builder()
                .contact(contact)
                .property(propertyDto)
                .id(UUID.randomUUID())
                .profile(CompanyType.BUYER.name())
                .build());
        }
        for (UUID titleCompany : command.getTitleEscrowCompany()) {
            CompanyContactDto contact = this.companyContactService.findById(titleCompany);
            list.add(PropertyTeamDto
                .builder()
                .contact(contact)
                .property(propertyDto)
                .id(UUID.randomUUID())
                .profile(CompanyType.TITLE_COMPANY.name())
                .build());
        }
        for (UUID lender : command.getLenderCompany()) {
            CompanyContactDto contact = this.companyContactService.findById(lender);
            list.add(PropertyTeamDto
                .builder()
                .contact(contact)
                .property(propertyDto)
                .id(UUID.randomUUID())
                .profile(CompanyType.LENDER.name())
                .build());
        }
        for (UUID projectManager : command.getProjectManager()) {
            CompanyContactDto contact = this.companyContactService.findById(projectManager);
            list.add(PropertyTeamDto
                .builder()
                .contact(contact)
                .property(propertyDto)
                .id(UUID.randomUUID())
                .profile("PROJECT_MANAGER")
                .build());
        }
        for (UUID legalContact : command.getLegalContact()) {
            CompanyContactDto contact = this.companyContactService.findById(legalContact);
            list.add(PropertyTeamDto
                .builder()
                .contact(contact)
                .property(propertyDto)
                .id(UUID.randomUUID())
                .profile("LEGAL_CONTACT")
                .build());
        }
        for (UUID seller : command.getSeller()) {
            CompanyContactDto contact = this.companyContactService.findById(seller);
            list.add(PropertyTeamDto
                .builder()
                .contact(contact)
                .property(propertyDto)
                .id(UUID.randomUUID())
                .profile(CompanyType.SELLER.name())
                .build());
        }
        for (UUID hoa : command.getHoa()) {
            CompanyContactDto contact = this.companyContactService.findById(hoa);
            list.add(PropertyTeamDto
                .builder()
                .contact(contact)
                .property(propertyDto)
                .id(UUID.randomUUID())
                .profile(CompanyType.HOA.name())
                .build());
        }

        //CREATE team
        this.propertyTeamService.create(list);

        //UPDATE Property
        propertyDto.setBuyerName(buyerEntityName);
        this.propertyService.update(propertyDto);
    }

}
