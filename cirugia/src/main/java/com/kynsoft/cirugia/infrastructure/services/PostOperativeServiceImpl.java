package com.kynsoft.cirugia.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.cirugia.application.query.postOperative.getBySurgeryId.PostOperativeResponse;
import com.kynsoft.cirugia.domain.dto.PostOperative;
import com.kynsoft.cirugia.domain.service.IPostOperativeService;
import com.kynsoft.cirugia.infrastructure.entities.PostOperativeEntity;
import com.kynsoft.cirugia.infrastructure.mapper.PostOperativeMapper;
import com.kynsoft.cirugia.infrastructure.repository.command.PostOperativeWriteRepository;
import com.kynsoft.cirugia.infrastructure.repository.query.PostOperativeReadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostOperativeServiceImpl implements IPostOperativeService {

    private final PostOperativeReadRepository postOperativeReadRepository;
    private final PostOperativeWriteRepository postOperativeWriteRepository;
    private final PostOperativeMapper mapper;

    @Override
    public Optional<PostOperative> findById(UUID id) {
        log.info("Finding post operative with ID: {}", id);
        return postOperativeReadRepository.findById(id)
                .map(mapper::toDto);
    }

    @Override
    public Optional<PostOperative> findBySurgeryId(UUID surgeryId) {
        log.info("Finding post operative by surgery ID: {}", surgeryId);
        return postOperativeReadRepository.findBySurgeryId(surgeryId)
                .map(mapper::toDto);
    }

    @Override
    @Transactional
    public PostOperative create(PostOperative postOperative) {
        log.info("Creating new post operative record: {}", postOperative);
        
        if (postOperative.getId() == null) {
            postOperative.setId(UUID.randomUUID());
        }
        
        postOperative.setCreatedAt(LocalDateTime.now());
        postOperative.setUpdatedAt(LocalDateTime.now());
        
        PostOperativeEntity entity = mapper.toEntity(postOperative);
        PostOperativeEntity savedEntity = postOperativeWriteRepository.save(entity);
        
        return mapper.toDto(savedEntity);
    }

    @Override
    @Transactional
    public PostOperative update(PostOperative postOperative) {
        log.info("Updating post operative with ID: {}", postOperative.getId());
        
        postOperative.setUpdatedAt(LocalDateTime.now());
        PostOperativeEntity entity = mapper.toEntity(postOperative);
        PostOperativeEntity savedEntity = postOperativeWriteRepository.save(entity);
        
        return mapper.toDto(savedEntity);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        log.info("Deleting post operative with ID: {}", id);
        postOperativeWriteRepository.deleteById(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        log.info("Searching post operatives with filter criteria: {}", filterCriteria);
        GenericSpecificationsBuilder<PostOperativeEntity> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<PostOperativeEntity> data = postOperativeReadRepository.findAll(specifications, pageable);
        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<PostOperativeEntity> data) {
        List<PostOperativeResponse> postOperativeResponses = new ArrayList<>();
        for (PostOperativeEntity entity : data.getContent()) {
            PostOperative postOperative = mapper.toDto(entity);
            postOperativeResponses.add(new PostOperativeResponse(postOperative));
        }
        return new PaginatedResponse(
            postOperativeResponses, 
            data.getTotalPages(), 
            data.getNumberOfElements(),
            data.getTotalElements(), 
            data.getSize(), 
            data.getNumber()
        );
    }
}