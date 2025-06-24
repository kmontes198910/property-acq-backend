package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.TitleCompanyResponse;
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
import com.kynsoft.propertyacqcenter.infrastructure.entity.embedded.company.TitleCompany;

@Service("titleCompanyService")
public class TitleCompanyService implements ICompanyService {

    private final CompanyWriteDataJPARepository repositoryCommand;

    private final CompanyReadDataJPARepository repositoryQuery;

    public TitleCompanyService(CompanyWriteDataJPARepository repositoryCommand, CompanyReadDataJPARepository repositoryQuery) {
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

            update.setTitleCompany(TitleCompany
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
                    .build()
            );
            // Guardar los cambios
            repositoryCommand.save(update);
        } else {
            throw new CompanyNotFoundException(dto.getId().toString(), "ID");
        }
    }

    @Override
    public CompanyDto findById(UUID id) {
        return this.repositoryQuery.findById(id)
                .map(Company::toAggregateTitleCompanySimple)
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
        List<TitleCompanyResponse> objects = new ArrayList<>();
        for (Company p : data.getContent()) {
            objects.add(new TitleCompanyResponse(p.toAggregateTitleCompany()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }
}
