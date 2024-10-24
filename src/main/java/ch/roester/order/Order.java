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
@Table(name = "order") // Changed to "orders" to avoid conflict with SQL reserved keywords
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @Column(name = "total_cost", precision = 10, scale = 2)
    private BigDecimal totalCost;

    // Updated to use the Status entity instead of an enum
    @ManyToOne
    @JoinColumn(name = "status", nullable = false, unique = true)
    @Enumerated(value = EnumType.STRING)
    private Status status; // Relationship to Status entity

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Position> positions;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Shipment> shipments; // Add the relationship to Shipment

    public Order() {
        this.status = new Status();
        this.status.setName(OrderStatus.WAITING_FOR_PAYMENT.toString());
    }

}
