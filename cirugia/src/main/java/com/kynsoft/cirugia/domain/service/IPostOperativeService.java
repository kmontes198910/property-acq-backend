package com.kynsoft.cirugia.domain.service;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.cirugia.domain.dto.PostOperative;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPostOperativeService {
    Optional<PostOperative> findById(UUID id);
    Optional<PostOperative> findBySurgeryId(UUID surgeryId);
    PostOperative create(PostOperative postOperative);
    PostOperative update(PostOperative postOperative);
    void delete(UUID id);
    PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria);
}