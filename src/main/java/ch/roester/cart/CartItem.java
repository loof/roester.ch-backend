package ch.roester.cart;
import ch.roester.app_user.AppUser;
import ch.roester.event.Event;
import ch.roester.variant.Variant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="cart_id", nullable=false)
    private Cart cart;

    @OneToOne(cascade =  CascadeType.ALL)
    @JoinColumn(name = "variant_id", referencedColumnName = "id", nullable = false)
    private Variant variant;

    @ManyToOne
    @JoinColumn(name="event_id")
    private Event event;

    @Column(name = "amount", nullable = false)
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