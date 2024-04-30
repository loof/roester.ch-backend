package ch.roester.product_size_price;

import ch.roester.product.Product;
import ch.roester.unit.Unit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product_size_price")
public class ProductSizePrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @ManyToOne
    @JoinColumn(name="product_id", nullable=false)
    private Product product;

    @ManyToOne
    @JoinColumn(name="unit_id", nullable=false)
    private Unit unit;

    @Column(name = "amount", nullable = false)
    private float amount;

    @Column(name = "price", nullable = false)
    private float price;

}
