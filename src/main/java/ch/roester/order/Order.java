package ch.roester.order;

import ch.roester.app_user.AppUser;
import ch.roester.product_event.ProductEvent;
import ch.roester.product_size_price.ProductSizePrice;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @Column(insertable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "product_size_price_unit_id")
    private ProductSizePrice productSizePrice;

    @Column(name = "amount", nullable = false)
    private int amount;
}
