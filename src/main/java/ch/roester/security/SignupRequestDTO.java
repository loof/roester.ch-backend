package ch.roester.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupRequestDTO {

    @NotBlank(message = "Vorname muss angegeben werden")
    private String firstname;

    @NotBlank(message = "Nachname muss angegeben werden")
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

    @NotBlank(message = "Strasse muss angegeben werden")
    private String street;

    @NotBlank(message = "Hausnummer muss angegeben werden")
    private String streetNumber;

    @NotBlank(message = "Ort muss angegeben werden")
    private String city;

    @NotBlank(message = "Postleitzahl muss angegeben werden")
    private String postalCode;

    private String companyName;

}
