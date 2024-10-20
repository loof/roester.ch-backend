package ch.roester.app_user;


import ch.roester.location.LocationRequestDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppUserRequestDTO {
    @NotBlank(message = "Vorname muss angegeben werden")
    private String firstname;

    @NotBlank(message = "Nachname muss angegeben werden")
    private String lastname;

    private LocationRequestDTO location;

    private String companyName;

    private boolean sendUpdates;
}
