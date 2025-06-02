package com.kynsof.treatments.infrastructure.repositories.query;

import com.kynsof.treatments.infrastructure.entity.ExternalConsultation;
import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ExternalConsultationReadDataJPARepository extends JpaRepository<ExternalConsultation, UUID>, JpaSpecificationExecutor<ExternalConsultation> {

    Page<ExternalConsultation> findAll(Specification specification, Pageable pageable);

    @Query("SELECT COUNT(ec) FROM ExternalConsultation ec JOIN ec.business b WHERE b.id = :businessId AND  ec.consultationTime >= :startDate AND ec.consultationTime < :endDate")
    Long countConsultationsByBusinessAndDateRange(@Param("businessId") UUID businessId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT MONTH(ec.consultationTime), COUNT(ec) " +
            "FROM ExternalConsultation ec " +
            "WHERE YEAR(ec.consultationTime) = :year " +
            "AND ec.business.id = :businessId " +
            "GROUP BY MONTH(ec.consultationTime) " +
            "ORDER BY MONTH(ec.consultationTime)")
    List<Object[]> countConsultationsByMonth(@Param("businessId") UUID businessId, @Param("year") int year);

    @Query("SELECT ec.service.name, COUNT(ec) " +
            "FROM ExternalConsultation ec " +
            "WHERE ec.business.id = :businessId " +
            "AND EXTRACT(YEAR FROM ec.consultationTime) = :year " +
            "GROUP BY ec.service.name " +
            "ORDER BY COUNT(ec) DESC")
    List<Object[]> findTop10SpecialitiesByServiceAndBusinessIdAndYear(
            @Param("businessId") UUID businessId,
            @Param("year") int year,
            Pageable pageable);

    @Query("SELECT d.icdCode, d.description, COUNT(d) " +
            "FROM ExternalConsultation ec " +
            "JOIN ec.diagnoses d " +
            "WHERE ec.business.id = :businessId " +
            "AND EXTRACT(YEAR FROM ec.consultationTime) = :year " +
            "GROUP BY d.icdCode, d.description " +
            "ORDER BY COUNT(d) DESC")
    List<Object[]> findTop10DiagnosesByBusinessAndYear(
            @Param("businessId") UUID businessId,
            @Param("year") int year,
            Pageable pageable);

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
