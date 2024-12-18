package com.kynsof.treatments.infrastructure.entity;

import com.kynsof.treatments.domain.dto.ConsultHistoryDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class ConsultHistory {
    @Id
    @Column(name="id")
    private UUID id;
    private String identification;
    private String observations;
    private String motive;
    private String physicalExamination;
    private int consultId;
    private String speciality;
    private String doctor;
    private Date consultDate;

    public ConsultHistory(ConsultHistoryDto entity) {
        this.id = entity.getId();
        this.identification = entity.getIdentification();
        this.observations = entity.getObservations();
        this.motive = entity.getMotive();
        this.physicalExamination = entity.getPhysicalExamination();
        this.consultId = entity.getConsultId();
        this.speciality = entity.getSpeciality();
        this.doctor = entity.getDoctor();
        this.consultDate = entity.getConsultDate();
    }

    public ConsultHistoryDto toAggregate() {

        return new ConsultHistoryDto(id,
                identification,
                observations,
                motive,
                physicalExamination,
                consultId,
                speciality,
                doctor,
                consultDate);

    }
}
