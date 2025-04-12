package com.kynsof.identity.infrastructure.entities;

import com.kynsof.identity.domain.dto.FirebaseTokenDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
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

    @OneToOne()
    @JoinColumn(name = "user_system_id", nullable = false)
    private UserSystem userSystem;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;


    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public FirebaseToken(FirebaseTokenDto dto) {
        this.id = dto.getId();
        this.userSystem = new UserSystem(dto.getUserSystemDto());
        this.token = dto.getToken();

    }
    public FirebaseTokenDto toAggregate() {

        return new FirebaseTokenDto(id,userSystem.toAggregate(),token );
    }
}