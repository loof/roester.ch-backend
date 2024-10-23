package ch.roester.part;
import ch.roester.base_entity.BaseEntity;
import ch.roester.product.Product;
import ch.roester.unit.Unit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "product_product")
public class Part extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "part_id")
    private Product part;

    @Column(name = "amount")
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Part that = (Part) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}