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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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
    private List<ProductEvent> productEvents;

    @OneToMany(mappedBy = "product")
    private List<ProductSizePrice> sizePrices;

    @Column(insertable = false)
    private LocalDateTime createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}