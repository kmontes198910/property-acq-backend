package com.kynsoft.cirugia.infrastructure.repository.command;

import com.kynsoft.cirugia.infrastructure.entities.PostOperativeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostOperativeWriteRepository extends JpaRepository<PostOperativeEntity, UUID> {
}