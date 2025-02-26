package com.kynsof.treatments.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class EvaluationQuestion {
    @Id
    @Column(name="id")
    private UUID id;

    private String code;  // Código único de la pregunta
    private String text;  // Texto de la pregunta
    private int maxScore;  // Puntuación máxima de la pregunta

    @ManyToOne
    @JoinColumn(name = "exam_type_id")
    private EvaluationExamenType examType;  // Relación con el tipo de examen
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}