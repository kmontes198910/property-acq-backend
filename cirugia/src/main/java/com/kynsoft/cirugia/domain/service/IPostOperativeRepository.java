package com.kynsoft.cirugia.domain.service;

import com.kynsoft.cirugia.domain.dto.PostOperative;

import java.util.Optional;
import java.util.UUID;

public interface IPostOperativeRepository {
    
    void save(PostOperative postOperative);
    
    Optional<PostOperative> findById(String id);
    
    Optional<PostOperative> findBySurgeryId(String surgeryId);
}