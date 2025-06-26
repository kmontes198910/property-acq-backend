package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.CompanyResponse;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.CompanyNotFoundException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.NotDeleteException;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.CompanyReadDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.CompanyWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.CompanyType;
import com.kynsoft.propertyacqcenter.infrastructure.entity.SubCategory;
import com.kynsoft.propertyacqcenter.infrastructure.entity.embedded.company.LegalInformation;
import com.kynsoft.propertyacqcenter.infrastructure.entity.embedded.company.Seller;
import com.kynsoft.propertyacqcenter.infrastructure.entity.embedded.company.TitleCompany;
import org.springframework.context.annotation.Primary;

@Service
@Primary
public class CompanyService implements ICompanyService {

    private final CompanyWriteDataJPARepository repositoryCommand;

    private final CompanyReadDataJPARepository repositoryQuery;

    public CompanyService(CompanyWriteDataJPARepository repositoryCommand, CompanyReadDataJPARepository repositoryQuery) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
    }

    @Override
    public UUID create(CompanyDto contactPersonDto) {
        return repositoryCommand.save(new Company(contactPersonDto)).getId();
    }

    @Override
    public void update(CompanyDto dto) {
        Optional<Company> company = this.repositoryQuery.findById(dto.getId());
        if (company.isPresent()) {
            Company update = company.get();

            update.setTitle(dto.getTitle());
            update.setNotes(dto.getNotes());
            update.setUpdatedBy(dto.getUpdatedBy());
            update.setCompanyType(dto.getCompanyType() != null ? new CompanyType(dto.getCompanyType()) : null);
            update.setCategory(dto.getCategory());
            update.setSubCategory(new SubCategory(dto.getSubCategory()));

            update.setTitleCompany(dto.getTitleCompany() != null ? TitleCompany
                    .builder()
                    .titleReview(dto.getTitleCompany().getTitleReview())
                    .copiesOfAnyExisting(dto.getTitleCompany().getCopiesOfAnyExisting())
                    .copyOfLastRecordedDeed(dto.getTitleCompany().getCopyOfLastRecordedDeed())
                    .existingTitlePolicy(dto.getTitleCompany().getExistingTitlePolicy())
                    .legalDescriptionOfTheProperty(dto.getTitleCompany().getLegalDescriptionOfTheProperty())
                    .oldTitleInsurancePolicy(dto.getTitleCompany().getOldTitleInsurancePolicy())
                    .taxCertificates(dto.getTitleCompany().getTaxCertificates())
                    .titleCommitment(dto.getTitleCompany().getTitleCommitment())
                    .uccSearchResults(dto.getTitleCompany().getUccSearchResults())
                    .build() : null
            );
            update.setSeller(dto.getSeller() != null ? Seller
                    .builder()
                    .id(dto.getSeller().getId())
                    .company(update)
                    .declareIfForeing(dto.getSeller().getDeclareIfForeing())
                    .folioParcelNumber(dto.getSeller().getFolioParcelNumber())
                    .legalDescription(dto.getSeller().getLegalDescription())
                    .lenderName(dto.getSeller().getLenderName())
                    .loanNumber(dto.getSeller().getLoanNumber())
                    .socialSecurity(dto.getSeller().getSocialSecurity())
                    .build() : null);
            update.setLegalInformation(dto.getLegalInformation() != null ? LegalInformation
                    .builder()
                    .id(dto.getLegalInformation().getId())
                    .company(update)
                    .annualRevenue(dto.getLegalInformation().getAnnualRevenue())
                    .authorizedSignerGovernmentIdCopy(dto.getLegalInformation().getAuthorizedSignerGovernmentIdCopy())
                    .authorizedSignerGovernmentIdCopyFileName(dto.getLegalInformation().getAuthorizedSignerGovernmentIdCopyFileName())
                    .businessDescription(dto.getLegalInformation().getBusinessDescription())
                    .dateOfLastAnnualReport(dto.getLegalInformation().getDateOfLastAnnualReport())
                    .entityExperience(dto.getLegalInformation().getEntityExperience())
                    .entityFico(dto.getLegalInformation().getEntityFico())
                    .entityType(dto.getLegalInformation().getEntityType())
                    .fiscalYearEnd(dto.getLegalInformation().getFiscalYearEnd())
                    .formationDate(dto.getLegalInformation().getFormationDate())
                    .formationState(dto.getLegalInformation().getFormationState())
                    .name(dto.getLegalInformation().getName())
                    .owner(dto.getLegalInformation().getOwner())
                    .taxId(dto.getLegalInformation().getTaxId())
                    .website(dto.getLegalInformation().getWebsite())
                    .build() : null);
            // Guardar los cambios
            repositoryCommand.save(update);
        } else {
            throw new CompanyNotFoundException(company.get().getId().toString(), "ID");
        }
    }

    @Override
    public CompanyDto findById(UUID id) {
        return this.repositoryQuery.findById(id)
                .map(Company::toAggregateSimple)
                .orElseThrow(() -> new CompanyNotFoundException(id.toString(), "ID"));
    }

    @Override
    public void delete(UUID id) {
        try {
            this.findById(id);
            repositoryCommand.deleteById(id);
        } catch (Exception e) {
            throw new NotDeleteException();
        }
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Company> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Company> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<Company> data) {
        List<CompanyResponse> objects = new ArrayList<>();
        for (Company p : data.getContent()) {
            objects.add(new CompanyResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }
}
