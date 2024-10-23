package ch.roester.event_product_amount;


import ch.roester.base_entity.BaseEntity;
import ch.roester.event.Event;
import ch.roester.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "event_product_amount")
public class EventProductAmount extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Double amountLeft;

    private Double amountTotal;

}
