package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.PurchaseResponse;
import com.kynsoft.propertyacqcenter.domain.dto.PurchaseDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.PurchaseForPropertyNotFoundException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.PurchaseNotFoundException;
import com.kynsoft.propertyacqcenter.domain.services.IPurchaseService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Property;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Purchase;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.PurchaseWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.PurchaseReadDataJPARepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;

@Service
public class PurchaseServiceImpl implements IPurchaseService {

    private final PurchaseReadDataJPARepository repositoryQuery;
    private final PurchaseWriteDataJPARepository repositoryCommand;

    public PurchaseServiceImpl(PurchaseReadDataJPARepository repositoryQuery,
                               PurchaseWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public UUID create(PurchaseDto object) {
        return repositoryCommand.save(new Purchase(object)).getId();
    }

    @Override
    @Transactional
    public void update(PurchaseDto object) {
        Purchase update = this.findByIdSimple(object.getId());
        update.setProperty(new Property(object.getProperty()));
        update.setEstimatedMarketValue(object.getEstimatedMarketValue());
        update.setForeclosureStatus(object.getForeclosureStatus());
        update.setImprovements(object.getImprovements());
        update.setPurchaseType(object.getPurchaseType());
        update.setPurchasePrice(object.getPurchasePrice());

        update.setAcHeatPump(object.getAcHeatPump());
        update.setBasement(object.getBasement());
        update.setCeiling(object.getCeiling());
        update.setDeck(object.getDeck());
        update.setElectrical(object.getElectrical());
        update.setExteriorPaint(object.getExteriorPaint());
        update.setFundation(object.getFundation());
        update.setHeating(object.getHeating());
        update.setKetchen(object.getKetchen());
        update.setPoolSpaRepair(object.getPoolSpaRepair());
        update.setSkylight(object.getSkylight());
        update.setOther(object.getOther());
        update.setAlarm(object.getAlarm());
        update.setBathroom(object.getBathroom());
        update.setChimney(object.getChimney());
        update.setDoor(object.getDoor());
        update.setEquipment(object.getEquipment());
        update.setFireplace(object.getFireplace());
        update.setGarage(object.getGarage());
        update.setInteriorPaint(object.getInteriorPaint());
        update.setLandscaping(object.getLandscaping());
        update.setPorch(object.getPorch());
        update.setWalls(object.getWalls());
        update.setAttic(object.getAttic());
        update.setCarpet(object.getCarpet());
        update.setCladding(object.getCladding());
        update.setDriveway(object.getDriveway());
        update.setExterior(object.getExterior());
        update.setFlooring(object.getFlooring());
        update.setGlutter(object.getGlutter());
        update.setIrrigationSpri(object.getIrrigationSpri());
        update.setPlumbing(object.getPlumbing());
        update.setRoof(object.getRoof());
        update.setWindowValue(object.getWindow());

        repositoryCommand.save(update);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        this.findByIdSimple(id);
        repositoryCommand.deleteById(id);
    }

    @Override
    public PurchaseDto findById(UUID id) {
        Optional<Purchase> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get().toAggregate();
        }
        throw new PurchaseNotFoundException(id);
    }

    private Purchase findByIdSimple(UUID id) {
        Optional<Purchase> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        }
        throw new PurchaseNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Purchase> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Purchase> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<Purchase> data) {
        List<PurchaseResponse> objects = new ArrayList<>();
        for (Purchase p : data.getContent()) {
            objects.add(new PurchaseResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public PurchaseDto findByPropertyId(String propertyId) {
        Optional<Purchase> entity = repositoryQuery.findByPropertyId(propertyId);
        if (entity.isPresent()) {
            return entity.get().toAggregate();
        }
        throw new PurchaseForPropertyNotFoundException();
    }
}
