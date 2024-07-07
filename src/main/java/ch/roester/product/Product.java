package ch.roester.product;

import ch.roester.event.Event;
import ch.roester.event_product_amount.EventProductAmount;
import ch.roester.property.Property;
import ch.roester.stock.Stock;
import ch.roester.tag.Tag;
import ch.roester.unit.Unit;
import ch.roester.variant.Variant;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
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
    private List<Variant> variants;

    @OneToMany(mappedBy = "product")
    List<EventProductAmount> eventProductAmounts;

    @ManyToOne
    @JoinColumn(name = "sold_unit_id")
    private Unit soldUnit;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @Column(name = "amount_in_stock")
    private double amountInStock;

    @Column(name = "price_per_unit")
    private BigDecimal pricePerUnit;

    @Column(insertable = false)
    private LocalDateTime createdAt;

    @Column(insertable = false)
    private LocalDateTime updatedAt;

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