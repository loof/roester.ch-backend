package ch.roester.order;

import ch.roester.app_user.AppUser;
import ch.roester.product_size_price.ProductSizePrice;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    protected Integer id;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @UpdateTimestamp
    private Instant lastUpdatedOn;

    @CreationTimestamp
    private Instant createdOn;

    @ManyToOne
    @JoinColumn(name = "product_size_price_unit_id")
    private ProductSizePrice productSizePrice;

    @Column(name = "amount", nullable = false)
    private int amount;
}
