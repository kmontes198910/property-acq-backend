package com.kynsof.evaluation.domain.service;

import com.kynsof.evaluation.domain.dto.BusinessDto;

import java.util.UUID;

public interface IBusinessService {
    public void create(BusinessDto object);
    public void update(BusinessDto object);
    public void delete(UUID id);
    public BusinessDto findById(UUID id);
}