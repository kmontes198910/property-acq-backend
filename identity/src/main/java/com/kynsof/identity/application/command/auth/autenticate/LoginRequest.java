package com.kynsof.identity.application.command.auth.autenticate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

import java.io.Serial;
import java.io.Serializable;

/**
 * Request object for user authentication.
 * 
 * This class represents the data required for user login,
 * including username and password with appropriate validations.
 */
@Value
@Builder
public class LoginRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -6587730290416571673L;
    
    /**
     * The username for authentication.
     * Must not be blank and have a length between 3 and 50 characters.
     */
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    String username;
    
    /**
     * The user's password for authentication.
     * Must not be blank, have a minimum length of 8 characters,
     * and follow security requirements.
     */
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
        message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character"
    )
    String password;
}
