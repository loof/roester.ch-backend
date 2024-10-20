package ch.roester.location;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationRequestDTO {
    private Integer id;

    @NotBlank
    private String street;

    @NotBlank
    private String streetNumber;

    @NotBlank
    private String city;

    @NotNull
    private String postalCode;

    private Double longitude;
    private Double latitude;
}
