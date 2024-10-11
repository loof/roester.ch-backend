package ch.roester.cart;
import ch.roester.app_user.AppUser;
import ch.roester.event.Event;
import ch.roester.event_product_amount.EventProductAmount;
import ch.roester.variant.Variant;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Check;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "cart_item", uniqueConstraints = { @UniqueConstraint(columnNames = { "cart_id", "event_id", "variant_id" }) })
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="cart_id", nullable=false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "variant_id", nullable = false)
    private Variant variant;

    @ManyToOne
    @JoinColumn(name="event_product_amount_id")
    private EventProductAmount eventProductAmount;

    @Column(name = "amount", nullable = false)
    @Min(1)
    private Double amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem that = (CartItem) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}