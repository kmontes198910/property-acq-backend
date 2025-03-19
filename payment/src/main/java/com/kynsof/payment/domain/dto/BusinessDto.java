package com.kynsof.payment.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessDto implements Serializable {
    private UUID id;
    private String name;
    private String logo;
    private String ruc;
    private String phone;
    private String email;
}
