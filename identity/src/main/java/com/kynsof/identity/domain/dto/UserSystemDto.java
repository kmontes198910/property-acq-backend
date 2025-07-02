package com.kynsof.identity.domain.dto;

import com.kynsof.share.core.domain.EUserType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserSystemDto implements Serializable {
    private UUID id;
    private String identification;
    private String email;
    private String userName;
    private String name;
    private String lastName;
    private UserStatus status;
    private LocalDate createdAt;
    private UUID selectedBusiness;


    private String image;
    private EUserType userType;
    private UUID keyCloakId;

    private List<ManageRolDto> roles;

    public UserSystemDto(UUID id, String userName, String email, String name,
                         String lastName, UserStatus status, String image) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.status = status;
        this.image = image;
    }


}
