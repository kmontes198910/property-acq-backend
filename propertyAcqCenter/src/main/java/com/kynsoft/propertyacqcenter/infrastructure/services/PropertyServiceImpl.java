package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.PropertiesResponse;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.NotDeleteException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.PropertyNotFoundException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.property.PropertyIdMustBeUniqueException;
import com.kynsoft.propertyacqcenter.domain.dto.projection.PropertyWithProfileDTO;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Contact;
import com.kynsoft.propertyacqcenter.infrastructure.entity.LegalEntity;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Property;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.PropertyWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.PropertyReadDataJPARepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;

@Service
public class PropertyServiceImpl implements IPropertyService {

    private final PropertyReadDataJPARepository repositoryQuery;
    private final PropertyWriteDataJPARepository repositoryCommand;

    public PropertyServiceImpl(PropertyReadDataJPARepository repositoryQuery,
            PropertyWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Transactional
    @Override
    public String create(PropertyDto object) {
        return repositoryCommand.save(new Property(object)).getId();
    }

    @Override
    @Transactional
    public void update(PropertyDto object) {
        Property update = new Property(object);
        update.setAddressLine1(object.getAddressLine1());
        update.setAddressLine2(object.getAddressLine2());
        update.setApn(object.getApn());
        update.setCity(object.getCity());
        update.setCounty(object.getCounty());
        update.setLotSize(object.getLotSize());
        update.setOccupancy(object.getOccupancy());
        update.setPropertyType(object.getPropertyType());
        update.setSquareFootage(object.getSquareFootage());
        update.setState(object.getState());
        update.setUnitCount(object.getUnitCount());
        update.setYearBuilt(object.getYearBuilt());
        update.setZipCode(object.getZipCode());
        update.setRoofType(object.getRoofType());
        update.setStructureType(object.getStructureType());
        update.setHoa(object.getHoa());
        update.setBedrooms(object.getBedrooms());
        update.setBathrooms(object.getBathrooms());
        update.setAskingPrice(object.getAskingPrice());
        update.setFormattedAddress(object.getFormattedAddress());
        update.setPropertyStatus(object.getPropertyStatus());
        update.setDaysOnMarket(object.getDaysOnMarket());
        update.setPurchasePrice(object.getPurchasePrice());
        update.setRentalPrice(object.getRentalPrice());
        update.setAfterRepairValue(object.getAfterRepairValue());
        update.setFloodZoneDetermination(object.getFloodZoneDetermination());
        update.setPropertyRented(object.getPropertyRented());

        //Acquisition
        update.setContractExecutionDate(object.getContractExecutionDate());
        update.setAcquisitionType(object.getAcquisitionType());
        update.setEmdOfferedAmount(object.getEmdOfferedAmount());
        update.setEmdRequirements(object.getEmdRequirements());
        update.setExpectedClosingDate(object.getExpectedClosingDate());
        update.setSellerContactInfo(object.getSellerContactInfo() != null ? new Contact(object.getSellerContactInfo()) : null);
        update.setSellerName(object.getSellerName() != null ? new LegalEntity(object.getSellerName()) : null);
        update.setBuyerName(object.getBuyerName() != null ? new LegalEntity(object.getBuyerName()) : null);
        update.setSourceType(object.getSourceType());

        //Mortagage
        update.setIsMortgage(object.getIsMortgage());
        update.setDistressed(object.getDistressed());
        update.setLenghOfOwership(object.getLenghOfOwership());
        update.setOpenBalanceMortagage(object.getOpenBalanceMortagage());
        update.setInvoluntaryLiensAmount(object.getInvoluntaryLiensAmount());

        //last sale
        update.setPublicRecord(object.getPublicRecord());
        update.setMls(object.getMls());

        update.setBuildingArea(object.getBuildingArea());
        update.setLivingArea(object.getLivingArea());
        update.setGrossArea(object.getGrossArea());
        update.setTaxableArea(object.getTaxableArea());
        update.setGarageArea(object.getGarageArea());

        //HOA
        update.setHasHoa(object.getHasHoa());
        update.setHoaName(object.getHoaName());
        update.setHoaType(object.getHoaType());
        update.setHoaFeeFrequency(object.getHoaFeeFrequency());

        repositoryCommand.save(update);
    }

    @Override
    public void delete(String id) {
        try {
            this.getById(id);
            repositoryCommand.deleteById(id);
        } catch (Exception e) {
            throw new NotDeleteException();
        }
    }

    @Override
    public PropertyDto getById(String id) {
        Optional<Property> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get().toAggregateSimple();
        }
        throw new PropertyNotFoundException(id);
    }

    private Property getByIdSimple(String id) {
        Optional<Property> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        }
        throw new PropertyNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Property> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Property> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<Property> data) {
        List<PropertiesResponse> objects = new ArrayList<>();
        for (Property p : data.getContent()) {
            objects.add(new PropertiesResponse(p.toAggregateSimple()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public PaginatedResponse searchWithProfileByContact(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Property> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Property> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponseWithProfileByContact(data);
    }

    private PaginatedResponse getPaginatedResponseWithProfileByContact(Page<Property> data) {
        List<PropertiesResponse> objects = new ArrayList<>();
        for (Property p : data.getContent()) {
            objects.add(new PropertiesResponse(p.toAggregateSimpleProfileByContact()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public long countById(String id) {
        return this.repositoryQuery.countById(id);
    }

    @Override
    public void validatePropertyId(String id) {
        long count = this.countById(id);
        if (count > 0) {
            throw new PropertyIdMustBeUniqueException(id);
        }
    }

    @Override
    public List<PropertyWithProfileDTO> findPropertiesWithProfileByContact(UUID contactId) {
        return this.repositoryQuery.findPropertiesWithProfileByContact(contactId);
    }

    @Override
    public void updateBuyerName(String property, LegalEntityDto buyer) {
        Property update = this.getByIdSimple(property);
        update.setBuyerName(new LegalEntity(buyer));
        repositoryCommand.save(update);
    }

}
