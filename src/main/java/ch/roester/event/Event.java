package ch.roester.event;

import ch.roester.location.Location;
import ch.roester.product_event.ProductEvent;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToMany(mappedBy = "event")
    private List<ProductEvent> productEvents;

}
