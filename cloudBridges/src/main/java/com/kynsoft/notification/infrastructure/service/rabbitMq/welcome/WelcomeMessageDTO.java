package com.kynsoft.notification.infrastructure.service.rabbitMq.welcome;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
@Getter
@Setter
public class WelcomeMessageDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String email;
    private String firstname;
    private  String lastname;
    private  String password;

    public WelcomeMessageDTO(String email, String firstname, String lastname, String password) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
    }

    @Override
    public String toString() {
        return "WelcomeMessageDTO{" +
                "email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}