package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.SubCategoryConstructionTypeResponse;
import com.kynsoft.propertyacqcenter.domain.services.ISubCategoryConstructionTypeService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.SubCategoryConstructionType;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.SubCategoryConstructionTypeWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.SubCategoryConstructionTypeReadDataJPARepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;

@Service
public class SubCategoryConstructionTypeServiceImpl implements ISubCategoryConstructionTypeService {

    private final SubCategoryConstructionTypeReadDataJPARepository repositoryQuery;
    private final SubCategoryConstructionTypeWriteDataJPARepository repositoryCommand;

    public SubCategoryConstructionTypeServiceImpl(SubCategoryConstructionTypeReadDataJPARepository repositoryQuery,
            SubCategoryConstructionTypeWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<SubCategoryConstructionType> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<SubCategoryConstructionType> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<SubCategoryConstructionType> data) {
        List<SubCategoryConstructionTypeResponse> objects = new ArrayList<>();
        for (SubCategoryConstructionType p : data.getContent()) {
            objects.add(new SubCategoryConstructionTypeResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public void create() {
        List<SubCategoryConstructionType> nomenclatorData = Arrays.asList(
                new SubCategoryConstructionType(UUID.randomUUID(), "General Contractors (GCs) – Oversee entire construction projects, hire subcontractors, manage timelines, and coordinate work on site."),
                new SubCategoryConstructionType(UUID.randomUUID(), "Construction Management Firms – Represent owners, manage budget, scheduling, quality control, often without self-performing work"),
                new SubCategoryConstructionType(UUID.randomUUID(), "Electrical Contractors"),
                new SubCategoryConstructionType(UUID.randomUUID(), "Plumbing & Mechanical Contractors"),
                new SubCategoryConstructionType(UUID.randomUUID(), "HVAC Contractors"),
                new SubCategoryConstructionType(UUID.randomUUID(), "Framing & Drywall Contractors"),
                new SubCategoryConstructionType(UUID.randomUUID(), "Roofing Companies"),
                new SubCategoryConstructionType(UUID.randomUUID(), "Masonry & Concrete Specialists"),
                new SubCategoryConstructionType(UUID.randomUUID(), "Flooring Installers"),
                new SubCategoryConstructionType(UUID.randomUUID(), "Painting & Finishing Crews"),
                new SubCategoryConstructionType(UUID.randomUUID(), "Landscaping & Hardscaping Firms"),
                new SubCategoryConstructionType(UUID.randomUUID(), "Insulation & Energy Efficiency Contractors"),
                new SubCategoryConstructionType(UUID.randomUUID(), "Glass & Glazing Companies"),
                new SubCategoryConstructionType(UUID.randomUUID(), "Low Voltage / Security System Installers"),
                new SubCategoryConstructionType(UUID.randomUUID(), "These companies are usually hired by the GC or builder and specialize in one scope of work."),
                new SubCategoryConstructionType(UUID.randomUUID(), "Custom Home Builders – Build one-off or small-scale homes to buyer specs."),
                new SubCategoryConstructionType(UUID.randomUUID(), "Production Home Builders – Build multiple homes using repeatable plans (e.g., DR Horton, Lennar)"),
                new SubCategoryConstructionType(UUID.randomUUID(), "Mixed-Use Developers – Combine residential, retail, and office in one project."),
                new SubCategoryConstructionType(UUID.randomUUID(), "Architectural Firms – Design structures and produce permit-ready plans."),
                new SubCategoryConstructionType(UUID.randomUUID(), "Civil Engineering Firms – Plan site layout, grading, stormwater, utilities, roads."),
                new SubCategoryConstructionType(UUID.randomUUID(), "Structural Engineering Firms – Ensure load-bearing integrity of buildings."),
                new SubCategoryConstructionType(UUID.randomUUID(), "MEP Engineering Firms – Specialize in mechanical, electrical, plumbing design."),
                new SubCategoryConstructionType(UUID.randomUUID(), "Interior Designers – Design internal spaces, finishes, and layouts."),
                new SubCategoryConstructionType(UUID.randomUUID(), "Surveying Companies – Provide land surveys, boundary verification, topography."),
                new SubCategoryConstructionType(UUID.randomUUID(), "Geotechnical Firms – Perform soil testing and foundation recommendations."),
                new SubCategoryConstructionType(UUID.randomUUID(), "Permit Expediting Services – Navigate local zoning and permit offices."),
                new SubCategoryConstructionType(UUID.randomUUID(), "6. Material Suppliers & Manufacturers"),
                new SubCategoryConstructionType(UUID.randomUUID(), "Lumber Yards"),
                new SubCategoryConstructionType(UUID.randomUUID(), "Concrete & Asphalt Suppliers"),
                new SubCategoryConstructionType(UUID.randomUUID(), "Steel Fabricators"),
                new SubCategoryConstructionType(UUID.randomUUID(), "Roofing & Siding Suppliers"),
                new SubCategoryConstructionType(UUID.randomUUID(), "Appliance & Cabinet Suppliers"),
                new SubCategoryConstructionType(UUID.randomUUID(), "Windows & Doors Distributors"),
                new SubCategoryConstructionType(UUID.randomUUID(), "Lighting & Electrical Supply Companies"),
                new SubCategoryConstructionType(UUID.randomUUID(), "Heavy Equipment Rental Companies – Provide excavators, cranes, lifts, etc."),
                new SubCategoryConstructionType(UUID.randomUUID(), "Logistics & Delivery Companies – Handle transport of materials and oversized loads."),
                new SubCategoryConstructionType(UUID.randomUUID(), "Dumpster & Waste Management Services"),
                new SubCategoryConstructionType(UUID.randomUUID(), "OSHA Safety Consultants"),
                new SubCategoryConstructionType(UUID.randomUUID(), "Third-Party Inspectors (City or Private)"),
                new SubCategoryConstructionType(UUID.randomUUID(), "QA/QC Specialists – Quality control managers ensuring standards are met."),
                new SubCategoryConstructionType(UUID.randomUUID(), "Environmental Compliance Firms – Handle dust, runoff, noise, etc."),
                new SubCategoryConstructionType(UUID.randomUUID(), "Construction Software Companies – (e.g., Procore, Buildertrend, CoConstruct)"),
                new SubCategoryConstructionType(UUID.randomUUID(), "BIM (Building Information Modeling) Services"),
                new SubCategoryConstructionType(UUID.randomUUID(), "Drone Surveying Firms"),
                new SubCategoryConstructionType(UUID.randomUUID(), "Project Scheduling & Estimating Firms"),
                new SubCategoryConstructionType(UUID.randomUUID(), "Punch List / Close-Out Crews – Handle final fixes before turnover."),
                new SubCategoryConstructionType(UUID.randomUUID(), "Warranty Service Providers – Respond to repair claims post-delivery."),
                new SubCategoryConstructionType(UUID.randomUUID(), "Facilities Maintenance Firms – Long-term building upkeep and systems care.")
        );

        repositoryCommand.saveAll(nomenclatorData);
    }
}
