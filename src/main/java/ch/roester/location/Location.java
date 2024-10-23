package ch.roester.location;

import ch.roester.app_user.AppUser;
import ch.roester.base_entity.BaseEntity;
import ch.roester.event.Event;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "location")
public class Location extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "street", nullable = false)
    @NotBlank(message = "Strasse muss angegeben werden")
    private String street;

    @Size(max = 100)
    @Column(name = "street_number", nullable = false, length = 100)
    @NotBlank(message = "Hausnummer muss angegeben werden")
    private String streetNumber;

    @Size(max = 100)
    @Column(name = "city", nullable = false, length = 100)
    @NotBlank(message = "Ort muss angegeben werden")
    private String city;

    @Column(name = "postal_code", nullable = false)
    @NotNull(message = "Postleitzahl muss angegeben werden")
    private String postalCode;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @OneToMany(mappedBy = "location")
    private Set<AppUser> appUsers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "location")
    private List<Event> events;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(id, location.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}