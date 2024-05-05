package ch.roester.product_order;

import ch.roester.order.Order;
import ch.roester.product.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product_order")
public class ProductOrder extends Order {

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
