package com.kynsof.payment.infrastructure.entity;

import com.kynsof.payment.domain.dto.ClientDto;
import com.kynsof.payment.domain.dto.enumDto.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Client {
    @Id
    @Column(name="id")
    private UUID id;
    private String identification;
    private String name;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Status status;

    public Client(ClientDto client) {
        this.id = client.getId();
        this.identification = client.getIdentification();
        this.name = client.getName();
        this.lastName = client.getLastName();
        this.status = client.getStatus();
    }

    public ClientDto toAggregate() {
        return new ClientDto(id, identification, name, lastName, status);
    }
}
