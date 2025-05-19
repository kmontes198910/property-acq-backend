package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.PropertiesResponse;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.PropertyNotFoundException;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Property;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.PropertyWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.PropertyReadDataJPARepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        update.setId(object.getId());
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

        repositoryCommand.save(update);
    }

    @Override
    @Transactional
    public void delete(String id) {
        this.getById(id);
        repositoryCommand.deleteById(id);
    }

    @Override
    public PropertyDto getById(String id) {
        Optional<Property> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get().toAggregate();
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
            objects.add(new PropertiesResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

}
