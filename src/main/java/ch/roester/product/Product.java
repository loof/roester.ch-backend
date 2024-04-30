package ch.roester.product;

import ch.roester.event.Event;
import ch.roester.property.Property;
import ch.roester.tag.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "products")
    private List<Event> events;

    @ManyToMany(mappedBy = "products")
    private List<Property> properties;

    @ManyToMany(mappedBy = "products")
    private Set<Tag> tags;

}