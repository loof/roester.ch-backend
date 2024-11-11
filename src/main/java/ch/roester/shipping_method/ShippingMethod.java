package ch.roester.shipping_method;

import ch.roester.base_entity.BaseEntity;
import ch.roester.carrier.Carrier;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "shipping_method")
public class ShippingMethod extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "carrier_id", nullable = false)
    private Carrier carrier;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "estimated_delivery_time")
    private String estimatedDeliveryTime;

    @Column(name = "description")
    private String description;

    @Column(name = "weight_in_grams_limit", nullable = false)
    private BigDecimal weightInGramsLimit;

    @Column(name = "inner_width_in_cm", nullable = false)
    private BigDecimal innerWidthInCm;

    @Column(name = "inner_height_in_cm", nullable = false)
    private BigDecimal innerHeightInCm;

    @Column(name = "inner_depth_in_cm", nullable = false)
    private BigDecimal innerDepthInCm;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShippingMethod that = (ShippingMethod) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}