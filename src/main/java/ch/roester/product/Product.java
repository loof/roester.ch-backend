package ch.roester.product;

import ch.roester.product_event.ProductEvent;
import ch.roester.product_order.ProductOrder;
import ch.roester.product_size_price.ProductSizePrice;
import ch.roester.property.Property;
import ch.roester.tag.Tag;
import jakarta.persistence.*;
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
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "products")
    private Set<Property> properties;

    @ManyToMany(mappedBy = "products")
    private Set<Tag> tags;

    @OneToMany(mappedBy = "product")
    private List<ProductOrder> orders;

    @OneToMany(mappedBy = "product")
    private List<ProductEvent> events;

    @OneToMany(mappedBy = "product")
    private List<ProductSizePrice> sizePrices;

}