package com.kynsof.treatments.infrastructure.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsof.treatments.application.query.optometryExam.getById.OptometryExamResponse;
import com.kynsof.treatments.domain.dto.OptometryExamDto;
import com.kynsof.treatments.domain.dto.enumDto.VaccinationStatus;
import com.kynsof.treatments.domain.service.IOptometryExamService;
import com.kynsof.treatments.infrastructure.entity.OptometryExam;
import com.kynsof.treatments.infrastructure.entity.Vaccine;
import com.kynsof.treatments.infrastructure.repositories.command.OptometryExamenWriteDataJPARepository;
import com.kynsof.treatments.infrastructure.repositories.query.OptometryExamenReadDataJPARepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OptometryExamServiceImpl implements IOptometryExamService {

    private final OptometryExamenWriteDataJPARepository repositoryCommand;
    private final OptometryExamenReadDataJPARepository repositoryQuery;

    public OptometryExamServiceImpl(OptometryExamenWriteDataJPARepository repositoryCommand, OptometryExamenReadDataJPARepository repositoryQuery) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
    }
    @Override
    public OptometryExamDto findById(UUID id) {
        OptometryExam exam = repositoryQuery.findById(id)
                .orElseThrow(() -> new RuntimeException("Optometry Exam not found"));
        return mapToDto(exam);
    }

    @Override
    public List<OptometryExamDto> findAll() {
        return repositoryQuery.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public UUID create(OptometryExamDto examDto) {
        OptometryExam exam = mapToEntity(examDto);
        return repositoryCommand.save(exam).getId();
    }

    @Override
    public void update(OptometryExamDto examDto) {
        OptometryExam exam = mapToEntity(examDto);
        repositoryCommand.save(exam);
    }

    @Override
    public void delete(UUID id) {
        repositoryCommand.deleteById(id);
    }

    @Override
    public OptometryExamDto getLastCurrentExamByPatient(UUID patientId) {
        return repositoryQuery.findTopByExternalConsultationPatientIdAndIsCurrentTrueOrderByCreatedAtDesc(patientId)
                .map(exam -> {
                    exam.setCurrent(false); // Cambia el campo isCurrent a false
                    return mapToDto(exam); // Convierte la entidad a DTO
                })
                .orElse(null);
    }

    @Override
    public void deleteByIds(List<UUID> optometryExamIdsToDelete) {
        repositoryCommand.deleteAllById(optometryExamIdsToDelete);
    }

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
        Page<OptometryExam> data = this.repositoryQuery.findAll(specifications, pageable);
        return createPaginatedResponse(data);
    }

    private PaginatedResponse createPaginatedResponse(Page<OptometryExam> data) {
        List<OptometryExamResponse> vaccineResponses = data.getContent().stream()
                .map(OptometryExam::toAggregate)
                .map(OptometryExamResponse::new)
                .collect(Collectors.toList());

        return new PaginatedResponse(vaccineResponses, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }


    private OptometryExamDto mapToDto(OptometryExam exam) {
        return new OptometryExamDto(
                exam.getId(),
                exam.getSphereOd(),
                exam.getCylinderOd(),
                exam.getAxisOd(),
                exam.getAvscOd(),
                exam.getAvccOd(),
                exam.getSphereOi(),
                exam.getCylinderOi(),
                exam.getAxisOi(),
                exam.getAvscOi(),
                exam.getAvccOi(),
                "",
                exam.getDp(),
                exam.getDv(),
                exam.getFilter(),
                exam.isCurrent(),
                exam.getAvccAdd(),
                exam.getSphereAdd(),
                exam.getCylinderAdd(),
                exam.getAvscAdd(),
                exam.getAxisAdd(),
                exam.getOrderNumber()
        );
    }

    private OptometryExam mapToEntity(OptometryExamDto dto) {
        OptometryExam exam = new OptometryExam();
        exam.setId(dto.getId());
        exam.setSphereOd(dto.getSphereOd());
        exam.setCylinderOd(dto.getCylinderOd());
        exam.setAxisOd(dto.getAxisOd());
        exam.setAvscOd(dto.getAvscOd());
        exam.setAvccOd(dto.getAvccOd());
        exam.setSphereOi(dto.getSphereOi());
        exam.setCylinderOi(dto.getCylinderOi());
        exam.setAxisOi(dto.getAxisOi());
        exam.setAvscOi(dto.getAvscOi());
        exam.setAvccOi(dto.getAvccOi());
     //   exam.setAddPower(dto.getAddPower());
        exam.setDp(dto.getDp());
        exam.setDv(dto.getDv());
        exam.setFilter(dto.getFilter());
        exam.setCurrent(dto.isCurrent());
        exam.setAvccAdd(dto.getAvccAdd());
        exam.setSphereAdd(dto.getSphereAdd());
        exam.setCylinderAdd(dto.getCylinderAdd());
        exam.setAvscAdd(dto.getAvscAdd());
        exam.setAxisAdd(dto.getAxisAdd());
        exam.setCurrent(dto.isCurrent());
        exam.setOrderNumber(dto.getOrderNumber());
        return exam;
    }
}
