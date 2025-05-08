package com.kynsoft.cirugia.domain.service;

import com.kynsoft.cirugia.domain.dto.Anesthesia;
import java.util.Optional;
import java.util.UUID;

/**
 * Interfaz que define las operaciones del servicio de anestesia siguiendo el patrón CQRS.
 * Separa claramente las operaciones de comando (modificaciones) y de consulta (lecturas).
 */
public interface IAnesthesiaService {
    
    /**
     * Comandos - Operaciones que modifican el estado
     */
    
    /**
     * Crea un nuevo registro de anestesia
     * @param anesthesia La anestesia a crear
     * @return El ID de la anestesia creada
     */
    String createAnesthesia(Anesthesia anesthesia);
    
    /**
     * Actualiza un registro de anestesia existente
     * @param anesthesia La anestesia con los datos actualizados
     */
    void updateAnesthesia(Anesthesia anesthesia);
    
    /**
     * Queries - Operaciones de solo lectura
     */
    
    /**
     * Obtiene una anestesia por su ID
     * @param id El ID de la anestesia
     * @return Optional con la anestesia si existe
     */
    Optional<Anesthesia> getAnesthesiaById(String id);
    
    /**
     * Obtiene una anestesia asociada a una cirugía específica
     * @param surgeryId El ID de la cirugía
     * @return Optional con la anestesia si existe
     */
    Optional<Anesthesia> getAnesthesiaBySurgeryId(UUID surgeryId);
}