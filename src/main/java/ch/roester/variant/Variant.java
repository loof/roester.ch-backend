package ch.roester.variant;

import ch.roester.product.Product;
import ch.roester.stock.Stock;
import ch.roester.unit.Unit;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "variant")
public class Variant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "stock_multiplier", nullable = false)
    private BigDecimal stockMultiplier = new BigDecimal(1);

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "display_unit_id")
    private Unit displayUnit;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @Column(name = "amount_in_stock")
    private Double amountInStock;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variant variant = (Variant) o;
        return Objects.equals(id, variant.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
