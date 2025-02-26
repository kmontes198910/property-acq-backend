package com.kynsof.treatments.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Evaluation {
    @Id
    @Column(name="id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patients patient;

    @OneToMany(mappedBy = "evaluationPatientExamen", cascade = CascadeType.ALL)
    private List<EvaluationPatientExam> evaluationPatientExams;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
