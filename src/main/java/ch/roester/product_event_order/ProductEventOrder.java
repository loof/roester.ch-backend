package ch.roester.product_event_order;

import ch.roester.order.Order;
import ch.roester.product_event.ProductEvent;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product_event_order")
public class ProductEventOrder extends Order {

    @ManyToOne
    @JoinColumn(name = "product_event_id")
    private ProductEvent productEvent;

}
