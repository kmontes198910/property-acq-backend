package com.kynsof.treatments.infrastructure.entity;

import com.kynsof.share.utils.GeneratorRandomNumber;
import com.kynsof.treatments.domain.dto.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class ExternalConsultation {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(nullable = false)
    private String referenceNumber;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patients patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Temporal(TemporalType.TIMESTAMP)
    private Date consultationTime;

    private String consultationReason;
    private String medicalHistory;
    private String physicalExam;
    private String medicalSpeciality;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "externalConsultation")
    private List<Diagnosis> diagnoses;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "externalConsultation")
    private List<Treatment> treatments;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,  fetch = FetchType.EAGER, mappedBy = "externalConsultation")
    private List<OptometryExam> optometryExams;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "externalConsultation")
    private List<Exam> exams;

    @ManyToOne
    @JoinColumn(name = "business_id")
    private Business business;
    private String observations;
    @ManyToOne
    @JoinColumn(name = "service_id", nullable = true)
    private Services service;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public ExternalConsultation(ExternalConsultationDto dto) {
        this.id = dto.getId();
        this.patient = new Patients(dto.getPatient());
        this.consultationReason = dto.getConsultationReason();
        this.medicalHistory = dto.getMedicalHistory();
        this.physicalExam = dto.getPhysicalExam();
        this.consultationTime = dto.getConsultationTime();
        this.doctor = new Doctor(dto.getDoctor());
        this.observations = dto.getObservations();
        this.medicalSpeciality = dto.getMedicalSpeciality();
        this.treatments = dto.getTreatments() != null ? dto.getTreatments().stream().map(t -> {
            Treatment treatment = new Treatment();
            treatment.setDescription(t.getDescription());
            treatment.setId(t.getId());
            treatment.setMedicineUnit(t.getMedicineUnit());
            treatment.setMedicines(new Medicines(t.getMedication()));
            treatment.setQuantity(t.getQuantity());
            treatment.setExternalConsultation(this);
            return treatment;
        }).toList() : new ArrayList<>();
        this.diagnoses = dto.getDiagnoses() != null ? dto.getDiagnoses().stream().map(d -> {
            Diagnosis diagnosis = new Diagnosis();
            diagnosis.setId(d.getId());
            diagnosis.setExternalConsultation(this);
            diagnosis.setDescription(d.getDescription());
            diagnosis.setIcdCode(d.getIcdCode());
            return diagnosis;
        }).toList() : new ArrayList<>();

        this.exams = dto.getExams() != null ? dto.getExams().stream().map(examDto -> {
            Exam exam = new Exam(examDto);
            exam.setExternalConsultation(this);
            return exam;
        }).toList() : new ArrayList<>();

        this.business = new Business(dto.getBusiness());
        this.service = new Services(dto.getService());
        this.referenceNumber = dto.getReferenceNumber() != null ? dto.getReferenceNumber() : GeneratorRandomNumber.generateRandomSecurity();
        this.optometryExams = dto.getOptometryExams() != null ? dto.getOptometryExams().stream().map(oe -> {
            OptometryExam optometryExam = new OptometryExam();
            optometryExam.setId(oe.getId() != null ? oe.getId() : UUID.randomUUID()); // Usa el ID existente o genera uno nuevo
            optometryExam.setSphereOd(oe.getSphereOd());
            optometryExam.setCylinderOd(oe.getCylinderOd());
            optometryExam.setAxisOd(oe.getAxisOd());
            optometryExam.setAvscOd(oe.getAvscOd());
            optometryExam.setAvccOd(oe.getAvccOd());
            optometryExam.setSphereOi(oe.getSphereOi());
            optometryExam.setCylinderOi(oe.getCylinderOi());
            optometryExam.setAxisOi(oe.getAxisOi());
            optometryExam.setAvscOi(oe.getAvscOi());
            optometryExam.setAvccOi(oe.getAvccOi());
            optometryExam.setDp(oe.getDp());
            optometryExam.setDv(oe.getDv());
            optometryExam.setFilter(oe.getFilter());
            optometryExam.setCurrent(oe.isCurrent());
            optometryExam.setAvccAdd(oe.getAvccAdd());
            optometryExam.setSphereAdd(oe.getSphereAdd());
            optometryExam.setCylinderAdd(oe.getCylinderAdd());
            optometryExam.setAvscAdd(oe.getAvscAdd());
            optometryExam.setAxisAdd(oe.getAxisAdd());
            optometryExam.setExternalConsultation(this);
            return optometryExam;
        }).toList() : new ArrayList<>();
    }

    public ExternalConsultationDto toAggregate() {
        List<TreatmentDto> treatmentList = this.getTreatments().stream()
                .map(treatment -> new TreatmentDto(treatment.getId(),treatment.getDescription(),
                        treatment.getMedicines().toAggregate(), treatment.getQuantity(), treatment.getMedicineUnit()))
                .toList();

        List<DiagnosisDto> diagnosisDtoList = this.getDiagnoses().stream()
                .map(treatment -> new DiagnosisDto(treatment.getId(),
                        treatment.getIcdCode(), treatment.getDescription()))
                .toList();

        List<ExamDto> exams = this.exams.stream().map(Exam::toAggregate).toList();

        List<OptometryExamDto> optometryExamDtoList = this.getOptometryExams().stream()
                .map(optometryExam -> new OptometryExamDto(
                        optometryExam.getId(),
                        optometryExam.getSphereOd(),
                        optometryExam.getCylinderOd(),
                        optometryExam.getAxisOd(),
                        optometryExam.getAvscOd(),
                        optometryExam.getAvccOd(),
                        optometryExam.getSphereOi(),
                        optometryExam.getCylinderOi(),
                        optometryExam.getAxisOi(),
                        optometryExam.getAvscOi(),
                        optometryExam.getAvccOi(),
                     "",
                        optometryExam.getDp(),
                        optometryExam.getDv(),
                        optometryExam.getFilter(),
                        optometryExam.isCurrent(),
                        optometryExam.getAvccAdd(),
                        optometryExam.getSphereAdd(),
                        optometryExam.getCylinderAdd(),
                        optometryExam.getAvscAdd(),
                        optometryExam.getAxisAdd(),
                        optometryExam.getOrderNumber()
                )).toList();

        return new ExternalConsultationDto(this.id, this.patient.toAggregate(), this.doctor.toAggregate(),
                this.consultationTime, this.consultationReason, this.medicalHistory, this.physicalExam, diagnosisDtoList,
                treatmentList, this.observations, exams, business.toAggregate(),
                medicalSpeciality, this.referenceNumber, this.service.toAggregate(),optometryExamDtoList);
    }

    @PrePersist
    protected void onCreate() {
        consultationTime = new Date();
    }
}
