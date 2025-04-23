package com.kynsof.treatments.domain.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.treatments.domain.dto.ExternalConsultationDto;
import com.kynsof.treatments.domain.dto.LabOrderDto;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IExternalConsultationService {

    UUID create(ExternalConsultationDto externalConsultation);

    UUID createAll(ExternalConsultationDto externalConsultation);

    UUID update(ExternalConsultationDto externalConsultation);

    void delete(ExternalConsultationDto dto);

    ExternalConsultationDto findById(UUID id);

   // PaginatedResponse findAll(Pageable pageable, UUID doctorId, UUID patientId);

    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filter);

    Long countConsultationsByBusinessAndDateRange(UUID businessId, Date startDate, Date endDate);
    List<Long> getConsultationsCountByMonth(UUID businessId,int year);

    List<Map<String, Object>> getTop10SpecialitiesByConsultationCount(UUID businessId, int year);

    List<Map<String, Object>> getTop10Diagnoses(UUID businessId, int year);

    /**
     * Obtiene todas las órdenes de laboratorio con filtros opcionales
     * @param pageable Información de paginación
     * @param patientId ID del paciente (opcional)
     * @param doctorId ID del doctor (opcional)
     * @param resourceId ID del recurso/examen (opcional)
     * @return Respuesta paginada con órdenes de laboratorio
     */
    PaginatedResponse findAllLabOrders(Pageable pageable, UUID patientId, UUID doctorId, UUID resourceId);

    /**
     * Obtiene todas las órdenes de laboratorio para una empresa específica con filtros opcionales
     * @param pageable Información de paginación
     * @param businessId ID de la empresa
     * @param patientId ID del paciente (opcional)
     * @param doctorId ID del doctor (opcional)
     * @param resourceId ID del recurso/examen (opcional)
     * @return Respuesta paginada con órdenes de laboratorio para la empresa especificada
     */
    PaginatedResponse findAllLabOrdersByBusiness(Pageable pageable, UUID businessId, UUID patientId, UUID doctorId, UUID resourceId);
}
