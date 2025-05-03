package com.kynsoft.cirugia.infrastructure.repository;

import com.kynsoft.cirugia.domain.dto.PostOperative;
import com.kynsoft.cirugia.domain.service.IPostOperativeRepository;
import com.kynsoft.cirugia.infrastructure.entities.PostOperativeEntity;
import com.kynsoft.cirugia.infrastructure.mapper.PostOperativeMapper;
import com.kynsoft.cirugia.infrastructure.repository.command.PostOperativeWriteRepository;
import com.kynsoft.cirugia.infrastructure.repository.query.PostOperativeReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PostOperativeRepository implements IPostOperativeRepository {

    private final PostOperativeReadRepository readRepository;
    private final PostOperativeWriteRepository writeRepository;
    private final PostOperativeMapper mapper;

    @Override
    public void save(PostOperative postOperative) {
        PostOperativeEntity entity = mapper.toEntity(postOperative);
        writeRepository.save(entity);
    }

    @Override
    public Optional<PostOperative> findById(String id) {
        return readRepository.findById(UUID.fromString(id))
                .map(mapper::toDto);
    }

    @Override
    public Optional<PostOperative> findBySurgeryId(String surgeryId) {
        return readRepository.findBySurgeryId(UUID.fromString(surgeryId))
                .map(mapper::toDto);
    }
}