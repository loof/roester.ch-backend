package ch.roester.product_event_order;

import ch.roester.app_user.AppUser;
import ch.roester.order.Order;
import ch.roester.product.Product;
import ch.roester.product_event.ProductEvent;
import ch.roester.product_size_price.ProductSizePrice;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "product_event_order")
public class ProductEventOrder extends Order {

    @ManyToOne
    @JoinColumn(name = "product_event_id")
    private ProductEvent productEvent;

}
