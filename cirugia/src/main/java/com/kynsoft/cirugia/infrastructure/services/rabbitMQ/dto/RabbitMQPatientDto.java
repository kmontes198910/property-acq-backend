package com.kynsoft.cirugia.infrastructure.services.rabbitMQ.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RabbitMQPatientDto {
    private UUID id;
    private String identification;
    private String email;
    private String name;
    private String lastName;
    private String image;
    private String profession;
    private String status;

    @Override
    public String toString() {
        return "RabbitMQPatientDto{" + "id=" + id + ", identification=" + identification + ", email=" + email + ", name=" + name + ", lastName=" + lastName + ", image=" + image + ", profession=" + profession + ", status=" + status + '}';
    }

}
