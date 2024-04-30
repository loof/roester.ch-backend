package ch.roester.product_order;

import ch.roester.app_user.AppUser;
import ch.roester.order.Order;
import ch.roester.product.Product;
import ch.roester.product_size_price.ProductSizePrice;
import jakarta.persistence.*;
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
