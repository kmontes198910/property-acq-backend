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
public class ElderlyPatientExamAnswer {
    @Id
    @Column(name="id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private EvaluationQuestion question;

    private boolean correct;  // Indica si la respuesta fue correcta
    private int scoreObtained;  // Puntaje obtenido en la pregunta

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
