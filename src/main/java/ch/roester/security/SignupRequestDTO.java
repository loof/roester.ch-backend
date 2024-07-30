package ch.roester.security;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupRequestDTO {

    @NotBlank(message = "firstname must not be empty")
    private String firstname;

    @NotBlank(message = "lastname must not be empty")
    private String lastname;

    @NotBlank(message = "email must not be empty")
    @Size(max = 45, message = "email length must be not more than 45")
    private String email;

    @NotBlank(message = "password must not be empty")
    @Size(min = 7, max = 255, message = "password length must be between 8 and 255")
    private String password;
}
