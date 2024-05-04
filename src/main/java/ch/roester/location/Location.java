package ch.roester.location;

import ch.roester.app_user.AppUser;
import ch.roester.event.Event;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "street", nullable = false)
    private String street;

    @Size(max = 100)
    @NotNull
    @Column(name = "street_nr", nullable = false, length = 100)
    private String streetNr;

    @Size(max = 100)
    @NotNull
    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @NotNull
    @Column(name = "postal_code", nullable = false)
    private Integer postalCode;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @OneToMany(mappedBy = "location")
    private Set<AppUser> appUsers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "location")
    private List<Event> events;

}