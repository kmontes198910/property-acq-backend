package com.kynsof.evaluation.infrastructure.entity;

import com.kynsof.evaluation.domain.dto.BusinessDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "business")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Business {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    public Business(BusinessDto business) {
        this.id = business.getId();
        this.name = business.getName();
    }

    public BusinessDto toAggregate() {
        return new BusinessDto(id, name);
    }

}
