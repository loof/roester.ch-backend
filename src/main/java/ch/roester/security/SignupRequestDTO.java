package ch.roester.security;

import ch.roester.location.LocationRequestDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDTO {

    private Integer id;

    private String firstname;

    private String lastname;

    @Email
    // RFC 2822 email validation
    //@Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="E-Mail Adresse ungültig")
    @NotBlank(message = "E-Mail muss angegeben werden")
    @Size(max = 45, message = "E-Mail Adresse ist zu lang")
    private String email;

    @NotBlank(message = "Passwort muss angegeben werden")
    @Size(min = 8, max = 255, message = "Passwortlänge muss zwischen 7 und 255 liegen")
    private String password;

    private LocationRequestDTO location;

    private String companyName;

}
