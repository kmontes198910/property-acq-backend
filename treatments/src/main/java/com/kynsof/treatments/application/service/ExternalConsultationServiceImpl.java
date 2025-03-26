package com.kynsof.treatments.application.service;

import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsof.share.utils.GeneratorRandomNumber;
import com.kynsof.treatments.application.query.externalConsultation.getall.ExternalConsultationResponse;
import com.kynsof.treatments.domain.dto.ExternalConsultationDto;
import com.kynsof.treatments.domain.dto.OptometryExamDto;
import com.kynsof.treatments.domain.service.IExternalConsultationService;
import com.kynsof.treatments.infrastructure.entity.ExternalConsultation;
import com.kynsof.treatments.infrastructure.entity.OptometryExam;
import com.kynsof.treatments.infrastructure.repositories.command.ExternalConsultationWriteDataJPARepository;
import com.kynsof.treatments.infrastructure.repositories.command.OptometryExamenWriteDataJPARepository;
import com.kynsof.treatments.infrastructure.repositories.query.ExternalConsultationReadDataJPARepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExternalConsultationServiceImpl implements IExternalConsultationService {

    private final ExternalConsultationWriteDataJPARepository repositoryCommand;
    private final ExternalConsultationReadDataJPARepository repositoryQuery;
    private final OptometryExamenWriteDataJPARepository repositoryOptometryExamen;

    public ExternalConsultationServiceImpl(ExternalConsultationWriteDataJPARepository repositoryCommand,
                                           ExternalConsultationReadDataJPARepository repositoryQuery, OptometryExamenWriteDataJPARepository repositoryOptometryExamen) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
        this.repositoryOptometryExamen = repositoryOptometryExamen;
    }

    @Override
    public UUID create(ExternalConsultationDto dto) {
        ExternalConsultation entity = new ExternalConsultation(dto);
        entity.setReferenceNumber(GeneratorRandomNumber.generateRandomSecurity());
        ExternalConsultation value =  this.repositoryCommand.save(entity);
        return value.getId();
    }

    @Override
    @Transactional
    public UUID createAll(ExternalConsultationDto dto) {
        return create(dto);
    }

    @Override
    public UUID update(ExternalConsultationDto dto) {
        ExternalConsultation update = this.repositoryQuery.findById(dto.getId()).orElseThrow();
        update.setConsultationReason(dto.getConsultationReason());
        update.setMedicalHistory(dto.getMedicalHistory());
        update.setObservations(dto.getObservations());
        update.setPhysicalExam(dto.getPhysicalExam());
        update.setOdontogramJson(dto.getOdontogramJson());


        if (dto.getOptometryExams()!= null && !dto.getOptometryExams().isEmpty()) {
            if (update.getOptometryExams() != null && !update.getOptometryExams().isEmpty()) {
                update.getOptometryExams().clear();

            }
            List<OptometryExam> newExams = dto.getOptometryExams().stream()
                    .filter(OptometryExamDto::isCurrent)
                    .map(optometryExamDto -> {
                        OptometryExam exam = new OptometryExam();
                        exam.setId(UUID.randomUUID());
                        exam.setSphereOd(optometryExamDto.getSphereOd());
                        exam.setCylinderOd(optometryExamDto.getCylinderOd());
                        exam.setAxisOd(optometryExamDto.getAxisOd());
                        exam.setAvscOd(optometryExamDto.getAvscOd());
                        exam.setAvccOd(optometryExamDto.getAvccOd());
                        exam.setSphereOi(optometryExamDto.getSphereOi());
                        exam.setCylinderOi(optometryExamDto.getCylinderOi());
                        exam.setAxisOi(optometryExamDto.getAxisOi());
                        exam.setAvscOi(optometryExamDto.getAvscOi());
                        exam.setAvccOi(optometryExamDto.getAvccOi());
                        exam.setDp(optometryExamDto.getDp());
                        exam.setDv(optometryExamDto.getDv());
                        exam.setFilter(optometryExamDto.getFilter());
                        exam.setCurrent(optometryExamDto.isCurrent());
                        exam.setAvccAdd(optometryExamDto.getAvccAdd());
                        exam.setSphereAdd(optometryExamDto.getSphereAdd());
                        exam.setCylinderAdd(optometryExamDto.getCylinderAdd());
                        exam.setAvscAdd(optometryExamDto.getAvscAdd());
                        exam.setAxisAdd(optometryExamDto.getAxisAdd());
                        exam.setCurrent(optometryExamDto.isCurrent());
                        exam.setExternalConsultation(update); // Relacionarlo con la consulta actual
                        return exam;
                    }).toList();

            update.setOptometryExams(newExams); // Asignar los nuevos exámenes
        }

        this.repositoryCommand.save(update);
        return update.getId();
    }

    @Override
    public ExternalConsultationDto findById(UUID id) {
        return this.repositoryQuery.findById(id)
                .map(ExternalConsultation::toAggregate)
                .orElseThrow(() -> new BusinessNotFoundException(
                        new GlobalBusinessException(DomainErrorMessage.EXTERNAL_CONSULTATION_NOT_FOUND,
                                new ErrorField("id", "External Consultation not found."))));
    }

//    @Override
//    public PaginatedResponse findAll(Pageable pageable, UUID doctorId, UUID patientId) {
//        ExternalConsultationSpecifications specifications = new ExternalConsultationSpecifications(doctorId, patientId);
//        Page<ExternalConsultation> data = this.repositoryQuery.findAll(specifications, pageable);
//        return createPaginatedResponse(data);
//    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filter) {
        GenericSpecificationsBuilder<ExternalConsultation> specifications = new GenericSpecificationsBuilder<>(filter);
        Page<ExternalConsultation> data = this.repositoryQuery.findAll(specifications, pageable);
        return createPaginatedResponse(data);
    }

    private PaginatedResponse createPaginatedResponse(Page<ExternalConsultation> data) {
        List<ExternalConsultationResponse> responses = data.getContent().stream()
                .map(ExternalConsultation::toAggregate)
                .map(ExternalConsultationResponse::new)
                .collect(Collectors.toList());

        return new PaginatedResponse(responses, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public void delete(ExternalConsultationDto dto) {
        try {
            this.repositoryCommand.deleteById(dto.getId());
        } catch (Exception e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(
                    DomainErrorMessage.NOT_DELETE,
                    new ErrorField("id", "Element cannot be deleted as it has a related element.")));
        }
    }

    @Override
    public Long countConsultationsByBusinessAndDateRange(UUID businessId, Date startDate, Date endDate) {
        return this.repositoryQuery.countConsultationsByBusinessAndDateRange(businessId, startDate, endDate);
    }

    @Override
    public List<Long> getConsultationsCountByMonth(UUID businessId, int year) {
        List<Object[]> results = repositoryQuery.countConsultationsByMonth(businessId, year);

        // Crear una lista con 12 meses inicializados en 0
        List<Long> monthlyCounts = new ArrayList<>(Collections.nCopies(12, 0L));

        // Llenar la lista con los valores obtenidos
        results.forEach(result -> {
            int month = (int) result[0] - 1; // Los meses en Java van de 0 a 11
            long count = (long) result[1];
            monthlyCounts.set(month, count);
        });

        return monthlyCounts;
    }

    @Override
    public List<Map<String, Object>> getTop10SpecialitiesByConsultationCount(UUID businessId, int year) {
        Pageable top10 = PageRequest.of(0, 10); // Limita a 10 registros
        List<Object[]> result = repositoryQuery.findTop10SpecialitiesByServiceAndBusinessIdAndYear(businessId, year, top10);

        return result.stream()
                .map(row -> Map.of(
                        "speciality", row[0],
                        "count", row[1]
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> getTop10Diagnoses(UUID businessId, int year) {
        Pageable top10 = PageRequest.of(0, 10); // Solo los 10 primeros resultados

        List<Object[]> results = repositoryQuery.findTop10DiagnosesByBusinessAndYear(businessId, year, top10);

        // Mapear los resultados a una lista de mapas para devolverlos al controlador
        return results.stream()
                .map(result -> Map.of(
                        "icdCode", result[0],
                        "description", result[1],
                        "count", result[2]
                ))
                .toList();
    }
}