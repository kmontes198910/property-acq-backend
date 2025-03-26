package com.kynsof.treatments.application.service;

import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsof.share.core.infrastructure.specifications.LogicalOperation;
import com.kynsof.share.core.infrastructure.specifications.SearchOperation;
import com.kynsof.treatments.application.query.vaccine.getall.VaccineResponse;
import com.kynsof.treatments.domain.dto.VaccineDto;
import com.kynsof.treatments.domain.dto.enumDto.VaccinationStatus;
import com.kynsof.treatments.domain.service.IVaccineService;
import com.kynsof.treatments.infrastructure.entity.Cie10;
import com.kynsof.treatments.infrastructure.entity.Vaccine;
import com.kynsof.treatments.infrastructure.repositories.command.VaccineWriteDataJPARepository;
import com.kynsof.treatments.infrastructure.repositories.query.PatientVaccineReadDataJPARepository;
import com.kynsof.treatments.infrastructure.repositories.query.VaccineReadDataJPARepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VaccineServiceImpl implements IVaccineService {

    private final VaccineWriteDataJPARepository repositoryCommand;

    private final VaccineReadDataJPARepository repositoryQuery;

    private final PatientVaccineReadDataJPARepository patientVaccineReadDataJPARepository;

    public VaccineServiceImpl(VaccineWriteDataJPARepository repositoryCommand, VaccineReadDataJPARepository repositoryQuery, PatientVaccineReadDataJPARepository patientVaccineReadDataJPARepository) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
        this.patientVaccineReadDataJPARepository = patientVaccineReadDataJPARepository;
    }

    @Override
    public VaccineDto findById(UUID id) {
        return this.repositoryQuery.findById(id)
                .map(Vaccine::toAggregate)
                .orElseThrow(() -> new BusinessNotFoundException(
                        new GlobalBusinessException(DomainErrorMessage.VACCINE_NOT_FOUND,
                                new ErrorField("id", "Vaccine not found."))));
    }

    @Override
    public void delete(VaccineDto object) {
        try {
            this.repositoryCommand.deleteById(object.getId());
        } catch (Exception e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(
                    DomainErrorMessage.NOT_DELETE,
                    new ErrorField("id", "Element cannot be deleted has a related element.")));
        }
    }

    @Override
    public PaginatedResponse getApplicableVaccines(LocalDate birthDate, UUID patientId, Pageable pageable) {
        int monthsOld = (int) ChronoUnit.MONTHS.between(birthDate, LocalDate.now());

        Page<Vaccine> vaccinePage = this.repositoryQuery.findByMinAgeLessThanEqualAndMaxAgeGreaterThanEqual(monthsOld, pageable);
        if (vaccinePage.isEmpty()) {
            return emptyPaginatedResponse(pageable);
        }

        Set<UUID> administeredVaccineIds = patientVaccineReadDataJPARepository.findVaccinesByPatientId(patientId)
                .stream()
                .map(Vaccine::getId)
                .collect(Collectors.toSet());

        List<VaccineResponse> nonAdministeredVaccines = vaccinePage.getContent().stream()
                .filter(vaccine -> !administeredVaccineIds.contains(vaccine.getId()))
                .map(vaccine -> new VaccineResponse(vaccine.toAggregate()))
                .collect(Collectors.toList());

        return createPaginatedResponse(nonAdministeredVaccines, vaccinePage);
    }

    @Override
    public UUID create(VaccineDto vaccineDto) {
        Vaccine entity = this.repositoryCommand.save(new Vaccine(vaccineDto));
        return entity.getId();
    }

    @Override
    public void update(VaccineDto vaccineDto) {
        Vaccine update = new Vaccine(vaccineDto);
        update.setUpdatedAt(LocalDateTime.now());
        this.repositoryCommand.save(update);
    }

//    @Override
//    public PaginatedResponse findAll(Pageable pageable, String name, String description) {
//        List<FilterCriteria> filterCriteria = new ArrayList<>();
//        filterCriteria.add(new FilterCriteria("description", SearchOperation.LIKE,description, LogicalOperation.OR));
//        filterCriteria.add(new FilterCriteria("name", SearchOperation.LIKE,name, LogicalOperation.OR));
//        var specifications = new GenericSpecificationsBuilder<Vaccine>(filterCriteria);
//        Page<Vaccine> data = this.repositoryQuery.findAll(specifications, pageable);
//        return createPaginatedResponse(data);
//    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        filterCriteria.forEach(filter -> {
            if ("status".equals(filter.getKey()) && filter.getValue() instanceof String) {
                try {
                    VaccinationStatus enumValue = VaccinationStatus.valueOf((String) filter.getValue());
                    filter.setValue(enumValue);
                } catch (IllegalArgumentException e) {
                    System.err.println("Valor inválido para el tipo Enum RoleStatus: " + filter.getValue());
                }
            }
        });

        GenericSpecificationsBuilder<Vaccine> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Vaccine> data = this.repositoryQuery.findAll(specifications, pageable);
        return createPaginatedResponse(data);
    }

    @Override
    public Long countByNameAndNotId(String name, UUID id) {
        return this.repositoryQuery.countByNameAndNotId(name, id);
    }

    private PaginatedResponse createPaginatedResponse(Page<Vaccine> data) {
        List<VaccineResponse> vaccineResponses = data.getContent().stream()
                .map(Vaccine::toAggregate)
                .map(VaccineResponse::new)
                .collect(Collectors.toList());

        return new PaginatedResponse(vaccineResponses, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    private PaginatedResponse createPaginatedResponse(List<VaccineResponse> vaccineResponses, Page<Vaccine> data) {
        return new PaginatedResponse(vaccineResponses, data.getTotalPages(), vaccineResponses.size(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    private PaginatedResponse emptyPaginatedResponse(Pageable pageable) {
        return new PaginatedResponse(
                List.of(),
                0,
                0,
                0L,
                pageable.getPageSize(),
                pageable.getPageNumber()
        );
    }
}