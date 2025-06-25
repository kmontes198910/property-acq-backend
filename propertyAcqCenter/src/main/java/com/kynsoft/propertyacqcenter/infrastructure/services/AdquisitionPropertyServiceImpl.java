package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.AdquisitionPropertyResponse;
import com.kynsoft.propertyacqcenter.domain.dto.AdquisitionPropertyDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.AddressNotFoundException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.NotDeleteException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.PurchaseForPropertyNotFoundException;
import com.kynsoft.propertyacqcenter.domain.services.IAdquisitionPropertyService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.AdquisitionProperty;
import com.kynsoft.propertyacqcenter.infrastructure.entity.CompanyContact;
import com.kynsoft.propertyacqcenter.infrastructure.entity.GeneralDocument;
import com.kynsoft.propertyacqcenter.infrastructure.entity.LegalEntity;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Property;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.AdquisitionPropertyWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.AdquisitionPropertyReadDataJPARepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;

@Service
@Primary
public class AdquisitionPropertyServiceImpl implements IAdquisitionPropertyService {

    private final AdquisitionPropertyReadDataJPARepository repositoryQuery;
    private final AdquisitionPropertyWriteDataJPARepository repositoryCommand;

    public AdquisitionPropertyServiceImpl(AdquisitionPropertyReadDataJPARepository repositoryQuery,
                                          AdquisitionPropertyWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public UUID create(AdquisitionPropertyDto object) {
        return repositoryCommand.save(new AdquisitionProperty(object)).getId();
    }

    @Override
    @Transactional
    public void update(AdquisitionPropertyDto object) {
        AdquisitionProperty update = this.findByIdSimple(object.getId());

        update.setBuyer(object.getBuyer() != null ? new LegalEntity(object.getBuyer()) : null);
        update.setContact(object.getContact() != null ? new CompanyContact(object.getContact()) : null);
        update.setProperty(new Property(object.getProperty()));

        update.setBuyerNameAndYearVehicle(object.getBuyerNameAndYearVehicle());
        update.setBuyerLicenseTagNo(object.getBuyerLicenseTagNo());

        update.setDateAndTimeForInspections(object.getDateAndTimeForInspections());
        update.setInstructionsForAccess(object.getInstructionsForAccess());
        update.setHoaBuyerInterviewDate(object.getHoaBuyerInterviewDate());
        update.setPreferredMoveinDate(object.getPreferredMoveinDate());
        update.setESignAuthorization(object.getESignAuthorization());
        update.setFinalWalkthroughDate(object.getFinalWalkthroughDate());
        update.setWireAccountHolderName(object.getWireAccountHolderName());
        update.setWireAccountNumber(object.getWireAccountNumber());
        update.setWireRoutingNumber(object.getWireRoutingNumber());
        update.setZelleEmailorPhone(object.getZelleEmailorPhone());
        update.setElectricProviderConfirmation(object.getElectricProviderConfirmation());
        update.setGasServiceConfirmation(object.getGasServiceConfirmation());
        update.setTrashServiceConfirmation(object.getTrashServiceConfirmation());
        update.setWaterSewerSetupConfirmation(object.getWaterSewerSetupConfirmation());

        update.setDocuments(object.getDocuments() != null
                ? object.getDocuments().stream().map(GeneralDocument::new).collect(Collectors.toSet())
                : Collections.emptySet());

        update.setUpdatedAt(LocalDateTime.now());
        repositoryCommand.save(update);
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
    public AdquisitionPropertyDto findById(UUID id) {
        Optional<AdquisitionProperty> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get().toAggregate();
        }
        throw new AddressNotFoundException(id);
    }

    private AdquisitionProperty findByIdSimple(UUID id) {
        Optional<AdquisitionProperty> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        }
        throw new AddressNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<AdquisitionProperty> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<AdquisitionProperty> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<AdquisitionProperty> data) {
        List<AdquisitionPropertyResponse> objects = new ArrayList<>();
        for (AdquisitionProperty p : data.getContent()) {
            objects.add(new AdquisitionPropertyResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public List<AdquisitionPropertyDto> findByPropertyId(String propertyId) {
        List<AdquisitionProperty> entity = repositoryQuery.findByPropertyId(propertyId);
        if (!entity.isEmpty()) {
            List<AdquisitionPropertyDto> list = new ArrayList<>();
            for (AdquisitionProperty adquisitionProperty : entity) {
                list.add(adquisitionProperty.toAggregate());
            }
            return list;
        }
        throw new PurchaseForPropertyNotFoundException();
    }

}
