package com.kynsoft.medicaltest.domain.service;

import com.kynsoft.medicaltest.domain.dto.LabTestParameterDto;

import java.util.List;
import java.util.UUID;

/**
 * Interfaz para el servicio de parámetros de exámenes de laboratorio
 */
public interface ILabTestParameterService {

    /**
     * Obtiene un parámetro de examen de laboratorio por su ID
     *
     * @param id El ID del parámetro de examen
     * @return El DTO del parámetro de examen de laboratorio
     */
    LabTestParameterDto getById(UUID id);

    /**
     * Obtiene todos los parámetros asociados a un examen de laboratorio
     *
     * @param labTestId El ID del examen de laboratorio
     * @return Lista de DTOs de parámetros
     */
    List<LabTestParameterDto> getByLabTestId(UUID labTestId);

    /**
     * Crea un nuevo parámetro de examen de laboratorio
     *
     * @param dto DTO con los datos del parámetro a crear
     * @param userId ID del usuario que realiza la acción
     * @return El DTO del parámetro creado
     */
    LabTestParameterDto create(LabTestParameterDto dto, UUID userId);

    /**
     * Actualiza un parámetro de examen de laboratorio existente
     *
     * @param id El ID del parámetro a actualizar
     * @param dto DTO con los nuevos datos
     * @param userId ID del usuario que realiza la acción
     * @return El DTO del parámetro actualizado
     */
    LabTestParameterDto update(UUID id, LabTestParameterDto dto, UUID userId);

    /**
     * Elimina un parámetro de examen de laboratorio
     *
     * @param id El ID del parámetro a eliminar
     */
    void delete(UUID id);
}
