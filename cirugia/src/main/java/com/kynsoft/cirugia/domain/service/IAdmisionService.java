package com.kynsoft.cirugia.domain.service;

import com.kynsoft.cirugia.domain.dto.AdmisionDto;

import java.util.Optional;
import java.util.UUID;

/**
 * Interface for the admission management service
 */
public interface IAdmisionService {
    /**
     * Creates a new admission
     * 
     * @param admision Admission data to create
     * @return ID of the created admission
     */
    UUID createAdmision(AdmisionDto admision);
    
    /**
     * Updates an existing admission
     * 
     * @param admision Admission data to update
     */
    void updateAdmision(AdmisionDto admision);
    
    /**
     * Gets the admission associated with a surgery
     * 
     * @param surgeryId ID of the surgery
     * @return Optional with the admission if it exists, or empty if not
     */
    Optional<AdmisionDto> getAdmisionBySurgeryId(UUID surgeryId);
    
    /**
     * Gets an admission by its ID
     * 
     * @param id ID of the admission
     * @return Optional with the admission if it exists, or empty if not
     */
    Optional<AdmisionDto> getAdmisionById(UUID id);
}
