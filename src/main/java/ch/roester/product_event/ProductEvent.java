package ch.roester.product_event;

import ch.roester.event.Event;
import ch.roester.location.Location;
import ch.roester.product.Product;
import ch.roester.product_event_order.ProductEventOrder;
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
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(name = "amount_available")
    private Integer amountAvailable;

    @ManyToOne
    @JoinColumn(name = "amount_available_unit_id")
    private Unit amountAvailableUnit;

    @OneToMany(mappedBy = "productEvent")
    private Set<ProductEventOrder> orders;
}



