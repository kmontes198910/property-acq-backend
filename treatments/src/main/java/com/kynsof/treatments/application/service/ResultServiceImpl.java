package com.kynsof.treatments.application.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsof.treatments.application.query.result.ResultResponse;
import com.kynsof.treatments.domain.dto.ExternalConsultationDto;
import com.kynsof.treatments.domain.dto.ResultDto;
import com.kynsof.treatments.domain.service.IResultService;
import com.kynsof.treatments.infrastructure.entity.ExternalConsultation;
import com.kynsof.treatments.infrastructure.entity.Result;
import com.kynsof.treatments.infrastructure.repositories.command.ResultWriteDataJPARepository;
import com.kynsof.treatments.infrastructure.repositories.query.ResultReadDataJPARepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ResultServiceImpl implements IResultService {

    private final ResultWriteDataJPARepository repositoryCommand;
    private final ResultReadDataJPARepository repositoryQuery;

    public ResultServiceImpl(ResultReadDataJPARepository repositoryQuery, ResultWriteDataJPARepository resultRepository) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = resultRepository;
    }

    @Override
    public ResultDto save(ResultDto resultDto, ExternalConsultationDto externalConsultation) {
        Result result = new Result();
        result.setId(resultDto.getId());
        result.setType(resultDto.getType());
        result.setUrl(resultDto.getUrl());
        result.setUploadedById(resultDto.getUploadedById());
        result.setUploadedByUsername(resultDto.getUploadedByUsername());
        result.setFileName(resultDto.getFileName());
        result.setFileType(resultDto.getFileType());
        result.setExternalConsultation(new ExternalConsultation(externalConsultation));
        
        Result savedResult = repositoryCommand.save(result);
        return mapToDto(savedResult);
    }

    @Override
    public ResultDto update(ResultDto resultDto) {
        Result result = repositoryCommand.findById(resultDto.getId())
                .orElseThrow(() -> new RuntimeException("Result not found with ID: " + resultDto.getId()));
        
        result.setType(resultDto.getType());
        result.setUrl(resultDto.getUrl());
        result.setUploadedById(resultDto.getUploadedById());
        result.setUploadedByUsername(resultDto.getUploadedByUsername());
        
        Result updatedResult = repositoryCommand.save(result);
        return mapToDto(updatedResult);
    }

    @Override
    public void delete(UUID id) {
        Result result = repositoryQuery.findById(id)
                .orElseThrow(() -> new RuntimeException("Result not found with ID: " + id));

        repositoryCommand.delete(result);
    }

    @Override
    public ResultDto findById(UUID id) {
        Result result = repositoryQuery.findById(id)
                .orElseThrow(() -> new RuntimeException("Result not found with ID: " + id));
        
        return mapToDto(result);
    }

    @Override
    public List<ResultDto> findByExternalConsultationId(UUID externalConsultationId) {
        return repositoryQuery.findByExternalConsultationId(externalConsultationId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria, String query) {
       // filterCreteria(filterCriteria);
        GenericSpecificationsBuilder<Result> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Result> data = this.repositoryQuery.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }
    private PaginatedResponse getPaginatedResponse(Page<Result> data) {
        List<ResultResponse> resultResponses = new ArrayList<>();
        for (Result o : data.getContent()) {
            resultResponses.add(new ResultResponse(o.getId(), o.getType(), o.getUrl(), o.getUploadedById(),
                    o.getUploadedByUsername(), o.getExternalConsultation() != null ? o.getExternalConsultation().getId() : null,
                    o.getCreatedAt(), o.getUpdatedAt(), o.getFileName(), o.getFileType()));
        }
        return new PaginatedResponse(resultResponses, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }
//    private void filterCreteria(List<FilterCriteria> filterCriteria) {
//        for (FilterCriteria filter : filterCriteria) {
//            if ("type".equals(filter.getKey()) && filter.getValue() instanceof String) {
//                try {
//                    MedicalExamCategory enumValue = MedicalExamCategory.valueOf((String) filter.getValue());
//                    filter.setValue(enumValue);
//                } catch (IllegalArgumentException e) {
//                    System.err.println("Valor inválido para el tipo Enum MedicalExamCategory: " + filter.getValue());
//                }
//            }
//        }
//    }
    
    private ResultDto mapToDto(Result result) {
        ResultDto resultDto = new ResultDto();
        resultDto.setId(result.getId());
        resultDto.setType(result.getType());
        resultDto.setUrl(result.getUrl());
        resultDto.setUploadedById(result.getUploadedById());
        resultDto.setUploadedByUsername(result.getUploadedByUsername());
        // No establecemos el externalConsultation para evitar ciclos
        return resultDto;
    }
}