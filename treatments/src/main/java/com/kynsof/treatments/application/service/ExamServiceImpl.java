package com.kynsof.treatments.application.service;

import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsof.treatments.application.query.exam.getbyid.ExamResponse;
import com.kynsof.treatments.domain.dto.ExamDto;
import com.kynsof.treatments.domain.service.IExamService;
import com.kynsof.treatments.infrastructure.entity.Exam;
import com.kynsof.treatments.infrastructure.entity.Procedure;
import com.kynsof.treatments.infrastructure.repositories.command.ExamWriteDataJPARepository;
import com.kynsof.treatments.infrastructure.repositories.query.ExamReadDataJPARepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ExamServiceImpl implements IExamService {

    private final ExamReadDataJPARepository repositoryQuery;

    private final ExamWriteDataJPARepository repositoryCommand;

    public ExamServiceImpl(ExamReadDataJPARepository repositoryQuery, ExamWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    public UUID create(ExamDto examDto) {
        return this.repositoryCommand.save(new Exam(examDto)).getId();
    }

    @Override
    public void update(ExamDto examDto) {
        Exam update = new Exam(examDto);
        update.setUpdatedAt(LocalDateTime.now());
        this.repositoryCommand.save(update);
    }

    @Override
    @Transactional
    public void delete(ExamDto examDto) {
        Exam diagnosis = repositoryCommand.findById(examDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Exam not found with id: " + examDto.getId()));

        repositoryCommand.deleteByCustomIdNative(diagnosis.getId());
        repositoryCommand.flush();
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<Procedure> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Exam> data = this.repositoryQuery.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<Exam> data) {
        List<ExamResponse> patients = new ArrayList<>();
        for (Exam o : data.getContent()) {
            patients.add(new ExamResponse(o.toAggregate()));
        }
        return new PaginatedResponse(patients, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public ExamDto findById(UUID id) {
        Optional<Exam> object = this.repositoryQuery.findById(id);
        if (object.isPresent()) {
            return object.get().toAggregate();
        }

        throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.EXAM_NOT_FOUND, new ErrorField("id", "Exam not found.")));
    }
}
