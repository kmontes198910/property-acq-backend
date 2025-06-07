package com.kynsof.hospitalizationService.infrastructure.service.rabbitMQ.consumer.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessRabbitMQDto {
    private UUID id;
    private String name;
    private String latitude;
    private String longitude;
    private String logo;
    private String ruc;
    private String address;
    private String email;
    private String phone;

    @Override
    public String toString() {
        return "BusinessRabbitMQDto{" + "id=" + id + ", name=" + name + ", latitude=" + latitude + ", longitude=" + longitude + ", logo=" + logo + ", ruc=" + ruc + ", address=" + address + ", email=" + email + ", phone=" + phone + '}';
    }

}
