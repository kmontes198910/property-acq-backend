package com.kynsof.treatments.domain.repository;

import com.kynsof.treatments.domain.dto.enumDto.MedicalExamCategory;
import com.kynsof.treatments.infrastructure.entity.ExternalConsultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IExternalConsultationRepository extends JpaRepository<ExternalConsultation, UUID> {

    /**
     * Busca todas las consultas externas que tengan al menos un examen de tipo LAB_TESTS
     * @return Lista de consultas externas con exámenes de tipo LAB_TESTS
     */
    @Query("SELECT DISTINCT ec FROM ExternalConsultation ec JOIN ec.exams e WHERE e.type = 'LAB_TESTS'")
    List<ExternalConsultation> findAllWithLabTests();

    /**
     * Busca todas las consultas externas que tengan al menos un examen de tipo LAB_TESTS para una empresa específica
     * @param businessId ID de la empresa
     * @return Lista de consultas externas con exámenes de tipo LAB_TESTS para la empresa especificada
     */
    @Query("SELECT DISTINCT ec FROM ExternalConsultation ec JOIN ec.exams e WHERE e.type = 'LAB_TESTS' AND ec.business.id = :businessId")
    List<ExternalConsultation> findAllWithLabTestsByBusinessId(@Param("businessId") UUID businessId);

    /**
     * Busca todas las consultas externas que tengan al menos un examen de tipo LAB_TESTS 
     * y filtra opcionalmente por paciente, doctor y/o ID del recurso/examen
     * @param patientId ID del paciente (opcional)
     * @param doctorId ID del doctor (opcional)
     * @param resourceId ID del recurso/examen (opcional)
     * @return Lista de consultas externas con exámenes de tipo LAB_TESTS filtradas según los parámetros
     */
    @Query("SELECT DISTINCT ec FROM ExternalConsultation ec JOIN ec.exams e WHERE e.type = 'LAB_TESTS' " +
           "AND (:patientId IS NULL OR ec.patient.id = :patientId) " +
           "AND (:doctorId IS NULL OR ec.doctor.id = :doctorId) " +
           "AND (:resourceId IS NULL OR e.id = :resourceId)")
    List<ExternalConsultation> findAllWithLabTestsFiltered(
            @Param("patientId") UUID patientId,
            @Param("doctorId") UUID doctorId,
            @Param("resourceId") UUID resourceId);

    /**
     * Busca todas las consultas externas que tengan al menos un examen de tipo LAB_TESTS para una empresa específica
     * y filtra opcionalmente por paciente, doctor y/o ID del recurso/examen
     * @param businessId ID de la empresa
     * @param patientId ID del paciente (opcional)
     * @param doctorId ID del doctor (opcional)
     * @param resourceId ID del recurso/examen (opcional)
     * @return Lista de consultas externas con exámenes de tipo LAB_TESTS para la empresa especificada y filtradas según los parámetros
     */
    @Query("SELECT DISTINCT ec FROM ExternalConsultation ec JOIN ec.exams e WHERE e.type = 'LAB_TESTS' AND ec.business.id = :businessId " +
           "AND (:patientId IS NULL OR ec.patient.id = :patientId) " +
           "AND (:doctorId IS NULL OR ec.doctor.id = :doctorId) " +
           "AND (:resourceId IS NULL OR e.id = :resourceId)")
    List<ExternalConsultation> findAllWithLabTestsByBusinessIdFiltered(
            @Param("businessId") UUID businessId,
            @Param("patientId") UUID patientId,
            @Param("doctorId") UUID doctorId,
            @Param("resourceId") UUID resourceId);
}