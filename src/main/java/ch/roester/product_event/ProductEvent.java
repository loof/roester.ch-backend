package ch.roester.product_event;

import ch.roester.event.Event;
import ch.roester.location.Location;
import ch.roester.product.Product;
import ch.roester.unit.Unit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "product_event")
public class ProductEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "amount_available")
    private Integer amountAvailable;

    @ManyToOne
    private Unit availableAmountUnit;
}



