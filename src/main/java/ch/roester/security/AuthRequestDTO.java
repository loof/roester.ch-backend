package ch.roester.security;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.Objects;

public class AuthRequestDTO {

    @NotBlank(message = "email must not be empty")
    @Size(max = 45, message = "email length must be not more than 45")
    private String email;
    @Getter
    @NotBlank(message = "password must not be empty")
    @Size(min = 7, max = 255, message = "password length must be between 8 and 255")
    private String password;


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AuthRequestDTO authRequestDTO)) {
            return false;
        }

        return email.equals(authRequestDTO.email)
                && password.equals(authRequestDTO.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
