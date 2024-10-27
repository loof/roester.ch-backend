package ch.roester.order;

import ch.roester.app_user.AppUser;
import ch.roester.base_entity.BaseEntity;
import ch.roester.shipment.Shipment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@EntityListeners(OrderEntityListener.class)
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    protected Integer id;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @Column(name = "total_cost", precision = 10, scale = 2)
    private BigDecimal totalCost;

    // Updated to use the Status entity instead of an enum
    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Status status; // Relationship to Status entity

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Position> positions;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Shipment> shipments;

    public Order() {
        this.status = new Status();
        this.status.setName(OrderStatus.WAITING_FOR_PAYMENT.toString());
    }

}
