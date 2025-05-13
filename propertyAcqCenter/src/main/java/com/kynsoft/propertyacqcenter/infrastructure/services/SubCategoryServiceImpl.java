package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.SubCategoryResponse;
import com.kynsoft.propertyacqcenter.domain.enums.ContactType;
import com.kynsoft.propertyacqcenter.infrastructure.entity.SubCategory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import com.kynsoft.propertyacqcenter.domain.services.ISubCategoryService;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.SubCategoryWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.SubCategoryReadDataJPARepository;

@Service
public class SubCategoryServiceImpl implements ISubCategoryService {

    private final SubCategoryReadDataJPARepository repositoryQuery;
    private final SubCategoryWriteDataJPARepository repositoryCommand;

    public SubCategoryServiceImpl(SubCategoryReadDataJPARepository repositoryQuery,
            SubCategoryWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<SubCategory> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<SubCategory> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<SubCategory> data) {
        List<SubCategoryResponse> objects = new ArrayList<>();
        for (SubCategory p : data.getContent()) {
            objects.add(new SubCategoryResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public void create() {
        List<SubCategory> list = new ArrayList<>();
        list.addAll(createConstructionType());
        list.addAll(createRealEstateCompanyType());
        repositoryCommand.saveAll(list);
    }

    private List<SubCategory> createConstructionType() {
        List<SubCategory> nomenclatorData = Arrays.asList(
                new SubCategory(UUID.randomUUID(), "General Contractors (GCs) – Oversee entire construction projects, hire subcontractors, manage timelines, and coordinate work on site.", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Construction Management Firms – Represent owners, manage budget, scheduling, quality control, often without self-performing work", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Electrical Contractors", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Plumbing & Mechanical Contractors", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "HVAC Contractors", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Framing & Drywall Contractors", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Roofing Companies", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Masonry & Concrete Specialists", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Flooring Installers", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Painting & Finishing Crews", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Landscaping & Hardscaping Firms", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Insulation & Energy Efficiency Contractors", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Glass & Glazing Companies", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Low Voltage / Security System Installers", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "These companies are usually hired by the GC or builder and specialize in one scope of work.", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Custom Home Builders – Build one-off or small-scale homes to buyer specs.", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Production Home Builders – Build multiple homes using repeatable plans (e.g., DR Horton, Lennar)", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Mixed-Use Developers – Combine residential, retail, and office in one project.", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Architectural Firms – Design structures and produce permit-ready plans.", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Civil Engineering Firms – Plan site layout, grading, stormwater, utilities, roads.", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Structural Engineering Firms – Ensure load-bearing integrity of buildings.", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "MEP Engineering Firms – Specialize in mechanical, electrical, plumbing design.", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Interior Designers – Design internal spaces, finishes, and layouts.", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Surveying Companies – Provide land surveys, boundary verification, topography.", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Geotechnical Firms – Perform soil testing and foundation recommendations.", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Permit Expediting Services – Navigate local zoning and permit offices.", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "6. Material Suppliers & Manufacturers", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Lumber Yards", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Concrete & Asphalt Suppliers", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Steel Fabricators", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Roofing & Siding Suppliers", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Appliance & Cabinet Suppliers", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Windows & Doors Distributors", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Lighting & Electrical Supply Companies", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Heavy Equipment Rental Companies – Provide excavators, cranes, lifts, etc.", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Logistics & Delivery Companies – Handle transport of materials and oversized loads.", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Dumpster & Waste Management Services", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "OSHA Safety Consultants", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Third-Party Inspectors (City or Private)", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "QA/QC Specialists – Quality control managers ensuring standards are met.", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Environmental Compliance Firms – Handle dust, runoff, noise, etc.", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Construction Software Companies – (e.g., Procore, Buildertrend, CoConstruct)", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "BIM (Building Information Modeling) Services", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Drone Surveying Firms", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Project Scheduling & Estimating Firms", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Punch List / Close-Out Crews – Handle final fixes before turnover.", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Warranty Service Providers – Respond to repair claims post-delivery.", ContactType.CONSTRUCTION_TYPE),
                new SubCategory(UUID.randomUUID(), "Facilities Maintenance Firms – Long-term building upkeep and systems care.", ContactType.CONSTRUCTION_TYPE)
        );
        return nomenclatorData;
    }

    private List<SubCategory> createRealEstateCompanyType() {
        List<SubCategory> nomenclatorData = Arrays.asList(
                new SubCategory(UUID.randomUUID(), "Lawyers", ContactType.REAL_ESTATE_COMPANY_TYPE),
                new SubCategory(UUID.randomUUID(), "Mortgage Title", ContactType.REAL_ESTATE_COMPANY_TYPE),
                new SubCategory(UUID.randomUUID(), "Mortgage Broker", ContactType.REAL_ESTATE_COMPANY_TYPE),
                new SubCategory(UUID.randomUUID(), "Lenders Single Family", ContactType.REAL_ESTATE_COMPANY_TYPE),
                new SubCategory(UUID.randomUUID(), "Lenders Multifamily Commercial", ContactType.REAL_ESTATE_COMPANY_TYPE),
                new SubCategory(UUID.randomUUID(), "Wholesalers", ContactType.REAL_ESTATE_COMPANY_TYPE),
                new SubCategory(UUID.randomUUID(), "Appraisers", ContactType.REAL_ESTATE_COMPANY_TYPE),
                new SubCategory(UUID.randomUUID(), "Surveyors", ContactType.REAL_ESTATE_COMPANY_TYPE),
                new SubCategory(UUID.randomUUID(), "Real Estate Investment Trusts (REITs) – Public or private firms that own income-producing properties.", ContactType.REAL_ESTATE_COMPANY_TYPE),
                new SubCategory(UUID.randomUUID(), "Private Equity Real Estate Firms – Pool investor funds to buy large-scale properties.", ContactType.REAL_ESTATE_COMPANY_TYPE),
                new SubCategory(UUID.randomUUID(), "Real Estate Syndicates – Groups of investors pooling capital for deals.", ContactType.REAL_ESTATE_COMPANY_TYPE),
                new SubCategory(UUID.randomUUID(), "Real Estate Developers – Plan and manage ground-up construction projects.", ContactType.REAL_ESTATE_COMPANY_TYPE),
                new SubCategory(UUID.randomUUID(), "Home Builders – Build residential homes, from custom homes to tract housing.", ContactType.REAL_ESTATE_COMPANY_TYPE),
                new SubCategory(UUID.randomUUID(), "General Contractors (GCs) – Oversee construction, subcontractors, and site management.", ContactType.REAL_ESTATE_COMPANY_TYPE),
                new SubCategory(UUID.randomUUID(), "Land Development Firms – Acquire raw land, subdivide, and prepare for construction or sale.", ContactType.REAL_ESTATE_COMPANY_TYPE),
                new SubCategory(UUID.randomUUID(), "Property Management Companies – Handle leasing, maintenance, rent collection, and tenant relations.", ContactType.REAL_ESTATE_COMPANY_TYPE),
                new SubCategory(UUID.randomUUID(), "Facilities Management Companies – Manage building operations and infrastructure, often for commercial assets.", ContactType.REAL_ESTATE_COMPANY_TYPE),
                new SubCategory(UUID.randomUUID(), "Short-Term Rental Management Firms", ContactType.REAL_ESTATE_COMPANY_TYPE),
                new SubCategory(UUID.randomUUID(), "Residential Real Estate Brokerages – Help individuals buy and sell homes.", ContactType.REAL_ESTATE_COMPANY_TYPE),
                new SubCategory(UUID.randomUUID(), "Commercial Brokerages – Specialize in office, retail, industrial, and multi-family transactions.", ContactType.REAL_ESTATE_COMPANY_TYPE),
                new SubCategory(UUID.randomUUID(), "Real Estate Agents / Realtors – Licensed individuals representing buyers or sellers.", ContactType.REAL_ESTATE_COMPANY_TYPE),
                new SubCategory(UUID.randomUUID(), "Title Companies – Conduct title searches, issue title insurance, and handle closings.", ContactType.REAL_ESTATE_COMPANY_TYPE),
                new SubCategory(UUID.randomUUID(), "Escrow Companies – Hold and disburse funds in real estate transactions.", ContactType.REAL_ESTATE_COMPANY_TYPE),
                new SubCategory(UUID.randomUUID(), "Real Estate Law Firms", ContactType.REAL_ESTATE_COMPANY_TYPE),
                new SubCategory(UUID.randomUUID(), "Mortgage Lenders & Banks – Provide financing for real estate purchases.", ContactType.REAL_ESTATE_COMPANY_TYPE),
                new SubCategory(UUID.randomUUID(), "Hard Money Lenders – Short-term, high-interest loans for investors/flippers.", ContactType.REAL_ESTATE_COMPANY_TYPE),
                new SubCategory(UUID.randomUUID(), "Private Lenders – Individuals or funds providing capital for deals.", ContactType.REAL_ESTATE_COMPANY_TYPE),
                new SubCategory(UUID.randomUUID(), "Loan Servicing Companies – Manage collection and recordkeeping of mortgage payments.", ContactType.REAL_ESTATE_COMPANY_TYPE),
                new SubCategory(UUID.randomUUID(), "Listing Platforms – Zillow, Redfin, Realtor.com", ContactType.REAL_ESTATE_COMPANY_TYPE),
                new SubCategory(UUID.randomUUID(), "Data & Analytics Firms – CoStar, CoreLogic, PropStream", ContactType.REAL_ESTATE_COMPANY_TYPE),
                new SubCategory(UUID.randomUUID(), "Property Management Software – AppFolio, Buildium, Stessa", ContactType.REAL_ESTATE_COMPANY_TYPE),
                new SubCategory(UUID.randomUUID(), "CRM & Workflow Tools – REIPro, InvestorFuse, Dealpath", ContactType.REAL_ESTATE_COMPANY_TYPE),
                new SubCategory(UUID.randomUUID(), "Appraisal Companies – Provide property valuations.", ContactType.REAL_ESTATE_COMPANY_TYPE),
                new SubCategory(UUID.randomUUID(), "Inspection Companies – Conduct structural, mechanical, and environmental inspections.", ContactType.REAL_ESTATE_COMPANY_TYPE),
                new SubCategory(UUID.randomUUID(), "Insurance Companies – Offer property, liability, and title insurance.", ContactType.REAL_ESTATE_COMPANY_TYPE),
                new SubCategory(UUID.randomUUID(), "HOA & Community Management Firms – Govern and manage residential communities.", ContactType.REAL_ESTATE_COMPANY_TYPE),
                new SubCategory(UUID.randomUUID(), "Homeowners", ContactType.REAL_ESTATE_COMPANY_TYPE)
        );

        return nomenclatorData;
    }
}
