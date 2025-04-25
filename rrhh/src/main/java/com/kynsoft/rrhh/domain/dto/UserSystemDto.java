package com.kynsoft.rrhh.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserSystemDto implements Serializable {
    private UUID id;
    private String identification;
    private String code;
    private String name;
    private String lastName;
    private String email;
    private String status;
    private String phoneNumber;
    private String image;
}
