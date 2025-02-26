package com.kynsof.treatments.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class EvaluationPatientExam {
    @Id
    @Column(name="id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "evaluation_id")
    private Evaluation evaluation;

    @ManyToOne
    @JoinColumn(name = "exam_type_id")
    private EvaluationExamenType examType; // Indica qué tipo de examen se realizó

    private LocalDate examDate;  // Fecha en que se realizó el examen
    private int totalScore;  // Puntuación total obtenida

    @OneToMany(mappedBy = "patientExam", cascade = CascadeType.ALL)
    private List<ElderlyPatientExamAnswer> answers;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}