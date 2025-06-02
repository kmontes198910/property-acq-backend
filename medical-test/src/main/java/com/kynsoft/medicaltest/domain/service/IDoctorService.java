package com.kynsoft.medicaltest.domain.service;

import com.kynsoft.medicaltest.domain.dto.DoctorDto;

import java.util.UUID;

public interface IDoctorService {
    UUID create(DoctorDto patients);
    UUID update(DoctorDto patients);
    void delete(UUID id);
    DoctorDto findById(UUID id);
}