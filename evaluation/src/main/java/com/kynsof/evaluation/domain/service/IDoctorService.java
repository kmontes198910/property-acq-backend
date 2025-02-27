package com.kynsof.evaluation.domain.service;

import com.kynsof.evaluation.domain.dto.DoctorDto;

import java.util.UUID;

public interface IDoctorService {
    UUID create(DoctorDto patients);
    UUID update(DoctorDto patients);
    void delete(UUID id);
    DoctorDto findById(UUID id);
}