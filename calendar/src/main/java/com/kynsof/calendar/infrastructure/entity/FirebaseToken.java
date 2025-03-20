package com.kynsof.calendar.infrastructure.entity;

import com.kynsof.calendar.domain.dto.FirebaseTokenDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FirebaseToken {

    @Id
    @GeneratedValue
    private UUID id;

    private String token;

    public FirebaseToken(FirebaseTokenDto dto) {
        this.id = dto.getId();
        this.token = dto.getToken();

    }

    public FirebaseTokenDto toAggregate() {
        return new FirebaseTokenDto(id, token);
    }
}
