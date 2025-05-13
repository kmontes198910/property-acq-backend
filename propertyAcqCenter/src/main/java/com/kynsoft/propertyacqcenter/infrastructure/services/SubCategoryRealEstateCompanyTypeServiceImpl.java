package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.SubCategoryRealEstateCompanyTypeResponse;
import com.kynsoft.propertyacqcenter.domain.services.ISubCategoryRealEstateCompanyTypeService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.SubCategoryRealEstateCompanyType;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.SubCategoryRealEstateCompanyTypeWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.SubCategoryRealEstateCompanyTypeReadDataJPARepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;

@Service
public class SubCategoryRealEstateCompanyTypeServiceImpl implements ISubCategoryRealEstateCompanyTypeService {

    private final SubCategoryRealEstateCompanyTypeReadDataJPARepository repositoryQuery;
    private final SubCategoryRealEstateCompanyTypeWriteDataJPARepository repositoryCommand;

    public SubCategoryRealEstateCompanyTypeServiceImpl(SubCategoryRealEstateCompanyTypeReadDataJPARepository repositoryQuery, 
                                                       SubCategoryRealEstateCompanyTypeWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<SubCategoryRealEstateCompanyType> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<SubCategoryRealEstateCompanyType> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<SubCategoryRealEstateCompanyType> data) {
        List<SubCategoryRealEstateCompanyTypeResponse> objects = new ArrayList<>();
        for (SubCategoryRealEstateCompanyType p : data.getContent()) {
            objects.add(new SubCategoryRealEstateCompanyTypeResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public void create() {
        List<SubCategoryRealEstateCompanyType> nomenclatorData = Arrays.asList(
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "Lawyers"),
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "Mortgage Title"),
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "Mortgage Broker"),
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "Lenders Single Family"),
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "Lenders Multifamily Commercial"),
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "Wholesalers"),
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "Appraisers"),
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "Surveyors"),
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "Real Estate Investment Trusts (REITs) – Public or private firms that own income-producing properties."),
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "Private Equity Real Estate Firms – Pool investor funds to buy large-scale properties."),
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "Real Estate Syndicates – Groups of investors pooling capital for deals."),
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "Real Estate Developers – Plan and manage ground-up construction projects."),
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "Home Builders – Build residential homes, from custom homes to tract housing."),
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "General Contractors (GCs) – Oversee construction, subcontractors, and site management."),
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "Land Development Firms – Acquire raw land, subdivide, and prepare for construction or sale."),
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "Property Management Companies – Handle leasing, maintenance, rent collection, and tenant relations."),
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "Facilities Management Companies – Manage building operations and infrastructure, often for commercial assets."),
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "Short-Term Rental Management Firms"),
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "Residential Real Estate Brokerages – Help individuals buy and sell homes."),
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "Commercial Brokerages – Specialize in office, retail, industrial, and multi-family transactions."),
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "Real Estate Agents / Realtors – Licensed individuals representing buyers or sellers."),
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "Title Companies – Conduct title searches, issue title insurance, and handle closings."),
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "Escrow Companies – Hold and disburse funds in real estate transactions."),
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "Real Estate Law Firms"),
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "Mortgage Lenders & Banks – Provide financing for real estate purchases."),
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "Hard Money Lenders – Short-term, high-interest loans for investors/flippers."),
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "Private Lenders – Individuals or funds providing capital for deals."),
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "Loan Servicing Companies – Manage collection and recordkeeping of mortgage payments."),
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "Listing Platforms – Zillow, Redfin, Realtor.com"),
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "Data & Analytics Firms – CoStar, CoreLogic, PropStream"),
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "Property Management Software – AppFolio, Buildium, Stessa"),
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "CRM & Workflow Tools – REIPro, InvestorFuse, Dealpath"),
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "Appraisal Companies – Provide property valuations."),
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "Inspection Companies – Conduct structural, mechanical, and environmental inspections."),
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "Insurance Companies – Offer property, liability, and title insurance."),
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "HOA & Community Management Firms – Govern and manage residential communities."),
                new SubCategoryRealEstateCompanyType(UUID.randomUUID(), "Homeowners")
        );

        repositoryCommand.saveAll(nomenclatorData);
    }

}
