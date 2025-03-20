package com.kynsof.evaluation.infrastructure.service;

import com.kynsof.evaluation.application.command.evaluationPatient.createSpecification.CodeAnswerRequest;
import com.kynsof.evaluation.application.command.evaluationPatient.updateSpecification.CodeAnswerUpdateRequest;
import com.kynsof.evaluation.domain.dto.EvaluationPatientExamDto;
import com.kynsof.evaluation.domain.dto.enumDto.EvaluationExamenType;
import com.kynsof.evaluation.domain.service.IEvaluationPatientService;
import com.kynsof.evaluation.infrastructure.entity.EvaluationPatientExam;
import com.kynsof.evaluation.infrastructure.entity.EvaluationPatientExamAnswer;
import com.kynsof.evaluation.infrastructure.entity.EvaluationQuestion;
import com.kynsof.evaluation.infrastructure.repositories.command.EvaluationPatientExamAnswerWriteDataJPARepository;
import com.kynsof.evaluation.infrastructure.repositories.command.EvaluationPatientWriteDataJPARepository;
import com.kynsof.evaluation.infrastructure.repositories.query.EvaluationPatientExamAnswerReadDataJPARepository;
import com.kynsof.evaluation.infrastructure.repositories.query.EvaluationPatientReadDataJPARepository;
import com.kynsof.evaluation.infrastructure.repositories.query.EvaluationQuestionReadDataJPARepository;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EvaluationPatientServiceImpl implements IEvaluationPatientService {

    private final EvaluationPatientWriteDataJPARepository repositoryCommand;

    private final EvaluationPatientReadDataJPARepository repositoryQuery;
    private final EvaluationQuestionReadDataJPARepository evaluationQuestionReadDataJPARepository;
    private final EvaluationPatientExamAnswerWriteDataJPARepository evaluationPatientExamAnswerWriteDataJPARepository;
    private final EvaluationPatientExamAnswerReadDataJPARepository evaluationPatientExamAnswerReadDataJPARepository;

    public EvaluationPatientServiceImpl(EvaluationPatientWriteDataJPARepository repositoryCommand,
                                        EvaluationPatientReadDataJPARepository repositoryQuery, EvaluationQuestionReadDataJPARepository evaluationQuestionPatientReadDataJPARepository, EvaluationPatientExamAnswerWriteDataJPARepository evaluationPatientExamAnswerWriteDataJPARepository, EvaluationPatientExamAnswerReadDataJPARepository evaluationPatientExamAnswerReadDataJPARepository) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
        this.evaluationQuestionReadDataJPARepository = evaluationQuestionPatientReadDataJPARepository;
        this.evaluationPatientExamAnswerWriteDataJPARepository = evaluationPatientExamAnswerWriteDataJPARepository;
        this.evaluationPatientExamAnswerReadDataJPARepository = evaluationPatientExamAnswerReadDataJPARepository;
    }

    @Override
    public UUID create(EvaluationPatientExamDto object, List<String> questionCodes) {

        List<EvaluationQuestion> evaluationQuestions = this.evaluationQuestionReadDataJPARepository.findByCodes(questionCodes);
        EvaluationPatientExam exam = new EvaluationPatientExam(object);
        long cantPoint = evaluationQuestions.stream()
                .mapToLong(EvaluationQuestion::getMaxScore)
                .sum();
        exam.setTotalScore((int) cantPoint);
        // Guardar el examen en la base de datos antes de asociarle respuestas
        exam = this.repositoryCommand.save(exam);


        EvaluationPatientExam finalExam = exam;
        List<EvaluationPatientExamAnswer> evaluationPatientExamAnswers = evaluationQuestions.stream()
                .map(question -> new EvaluationPatientExamAnswer(finalExam, question, true, question.getMaxScore(), ""))
                .toList(); // Convertir a lista para ser guardado

        this.evaluationPatientExamAnswerWriteDataJPARepository.saveAll(evaluationPatientExamAnswers);
        return exam.getId();
    }

    public EvaluationPatientExam getExamByEvaluationIdAndType(UUID evaluationId, EvaluationExamenType examenType) {
        return this.repositoryQuery.findByEvaluationIdAndExamType(evaluationId, examenType)
                .orElse(null);
    }

    @Override
    public UUID createSpecification(EvaluationPatientExamDto evaluationPatientExamDto, List<CodeAnswerRequest> examenListCode) {
        List<String> questionCodes = examenListCode.stream()
                .map(CodeAnswerRequest::getCode)
                .toList();

        List<EvaluationQuestion> evaluationQuestions = this.evaluationQuestionReadDataJPARepository.findByCodes(questionCodes);
        EvaluationPatientExam exam = new EvaluationPatientExam(evaluationPatientExamDto);

        long totalPoints = evaluationQuestions.stream()
                .mapToLong(EvaluationQuestion::getMaxScore)
                .sum();
        exam.setTotalScore((int) totalPoints);

        // Guardar el examen en la base de datos antes de asociarle respuestas
        exam = this.repositoryCommand.save(exam);

        Map<String, String> responseMap = examenListCode.stream()
                .collect(Collectors.toMap(CodeAnswerRequest::getCode, CodeAnswerRequest::getResponse));

        EvaluationPatientExam finalExam = exam;
        List<EvaluationPatientExamAnswer> evaluationPatientExamAnswers = evaluationQuestions.stream()
                .map(question -> new EvaluationPatientExamAnswer(finalExam, question, true, question.getMaxScore(),
                        responseMap.getOrDefault(question.getCode(), "")))
                .toList();

        // Guardar las respuestas en la base de datos
        this.evaluationPatientExamAnswerWriteDataJPARepository.saveAll(evaluationPatientExamAnswers);

        return exam.getId();
    }

    @Override
    public void updateSpecification(EvaluationPatientExamDto object, List<CodeAnswerUpdateRequest> examenListCode) {
        EvaluationPatientExam evaluationPatientExam = this.repositoryQuery.findById(object.getId())
                .orElseThrow(() -> new RuntimeException("EvaluationPatientExam not found"));

        List<UUID> eval = this.evaluationPatientExamAnswerReadDataJPARepository.findByPatientExamId(evaluationPatientExam.getId()).stream().map(EvaluationPatientExamAnswer::getId).toList();

        this.evaluationPatientExamAnswerWriteDataJPARepository.deleteAllByIdInBatch(eval);

        List<String> questionCodes = examenListCode.stream()
                .map(CodeAnswerUpdateRequest::getCode)
                .toList();

        List<EvaluationQuestion> evaluationQuestions = this.evaluationQuestionReadDataJPARepository.findByCodes(questionCodes);

        long totalPoints = evaluationQuestions.stream()
                .mapToLong(EvaluationQuestion::getMaxScore)
                .sum();
        evaluationPatientExam.setTotalScore((int) totalPoints);
        Map<String, String> responseMap = examenListCode.stream()
                .collect(Collectors.toMap(CodeAnswerUpdateRequest::getCode, CodeAnswerUpdateRequest::getResponse));
        List<EvaluationPatientExamAnswer> evaluationPatientExamAnswers = evaluationQuestions.stream()
                .map(question -> new EvaluationPatientExamAnswer(evaluationPatientExam, question, true, question.getMaxScore(), responseMap.getOrDefault(question.getCode(), "")))
                .toList();

        evaluationPatientExam.setAnswers(evaluationPatientExamAnswers);
        this.repositoryCommand.save(evaluationPatientExam);
    }


    @Override
    public void update(EvaluationPatientExamDto object, List<String> answers) {

        // Buscar el examen, lanzar excepción si no existe
        EvaluationPatientExam evaluationPatientExam = this.repositoryQuery.findById(object.getId())
                .orElseThrow(() -> new RuntimeException("EvaluationPatientExam not found"));
        List<UUID> eval = this.evaluationPatientExamAnswerReadDataJPARepository.findByPatientExamId(evaluationPatientExam.getId()).stream().map(EvaluationPatientExamAnswer::getId).toList();

        this.evaluationPatientExamAnswerWriteDataJPARepository.deleteAllByIdInBatch(eval);

        List<EvaluationQuestion> evaluationQuestions = this.evaluationQuestionReadDataJPARepository.findByCodes(answers);

        long cantPoint = evaluationQuestions.stream()
                .mapToLong(EvaluationQuestion::getMaxScore)
                .sum();
        evaluationPatientExam.setTotalScore((int) cantPoint);

        List<EvaluationPatientExamAnswer> evaluationPatientExamAnswers = evaluationQuestions.stream()
                .map(question -> new EvaluationPatientExamAnswer(evaluationPatientExam, question, true, question.getMaxScore(), ""))
                .toList();

        evaluationPatientExam.setAnswers(evaluationPatientExamAnswers);
        this.repositoryCommand.save(evaluationPatientExam);
    }

    @Override
    public void delete(UUID id) {

    }

    @Override
    public EvaluationPatientExamDto findByIds(UUID id) {
        return this.repositoryQuery.findById(id).get().toAggregate();
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        filterCriteria(filterCriteria);
//                GenericSpecificationsBuilder<Evaluation> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
//        Page<Evaluation> data = this.repositoryQuery.findAll(specifications, pageable);
//        return getPaginatedResponse(data);
        return null;

    }

    private void filterCriteria(List<FilterCriteria> filterCriteria) {
        filterCriteria.forEach(filter -> {
            if ("examenType".equals(filter.getKey()) && filter.getValue() instanceof String) {
                filter.setValue(parseEnum(EvaluationExamenType.class, (String) filter.getValue(), "EvaluationExamenType"));
            }
        });
    }

    private <T extends Enum<T>> T parseEnum(Class<T> enumClass, String value, String enumName) {
        try {
            return Enum.valueOf(enumClass, value);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid value for enum " + enumName + ": " + value);
            return null;
        }
    }
//
//    @Override
//    public void update(EvaluationPatientExamDto objectDto) {
//      Evaluation evaluation = this.repositoryQuery.findById(objectDto.getId()).orElseThrow();
//
//        this.repositoryCommand.save(evaluation);
//    }
//
//    @Override
//    public void delete(UUID id) {
//        try {
//            this.repositoryCommand.deleteById(id);
//        } catch (Exception e) {
//            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.NOT_DELETE, new ErrorField("id", "Element cannot be deleted has a related element.")));
//        }
//    }
//
//    @Override
//    public EvaluationPatientExamDto findByIds(UUID id) {
//
//        Optional<Evaluation> object = this.repositoryQuery.findById(id);
//        if (object.isPresent()) {
//            return object.get().toAggregate();
//        }
//        throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.SERVICE_NOT_FOUND, new ErrorField("id", "Service not found.")));
//
//    }
//
//    @Override
//    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
//        GenericSpecificationsBuilder<Evaluation> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
//        Page<Evaluation> data = this.repositoryQuery.findAll(specifications, pageable);
//        return getPaginatedResponse(data);
//    }
//
//    private PaginatedResponse getPaginatedResponse(Page<EvaluationPatientExam> data) {
//        List<EvaluationResponse> servicesResponses = new ArrayList<>();
//        for (Evaluation s : data.getContent()) {
//            servicesResponses.add(new EvaluationResponse(s.toAggregate()));
//        }
//        return new PaginatedResponse(servicesResponses, data.getTotalPages(), data.getNumberOfElements(),
//                data.getTotalElements(), data.getSize(), data.getNumber());
//    }

}
