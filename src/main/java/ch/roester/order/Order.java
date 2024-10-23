package ch.roester.order;

import ch.roester.app_user.AppUser;
import ch.roester.base_entity.BaseEntity;
import ch.roester.shipment.Shipment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "order")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @Column(name = "total_cost", precision = 10, scale = 2)
    private BigDecimal totalCost;

    @Enumerated(EnumType.STRING) // Store the enum as a string in the database
    @Column(name = "status", nullable = false)
    private OrderStatus status; // Use the OrderStatus enum

    @OneToMany(mappedBy = "order")
    private List<Position> positions;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Shipment> shipments; // Add the relationship to Shipment


}
