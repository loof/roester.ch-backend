package ch.roester.shipment;

import ch.roester.carrier.Carrier;
import ch.roester.order.Order;
import ch.roester.shipping_method.ShippingMethod;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Shipment")
@Getter
@Setter
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;  // Reference to Order entity

    @ManyToOne
    @JoinColumn(name = "shipping_method_id", nullable = false)
    private ShippingMethod shippingMethod;  // Reference to ShippingMethod entity

    @ManyToOne
    @JoinColumn(name = "carrier_id", nullable = false)
    private Carrier carrier;  // Reference to Carrier entity

    @Column(name = "shipment_cost", precision = 10, scale = 2)
    private BigDecimal shipmentCost;

    @Column(name = "shipment_date")
    private LocalDate shipmentDate;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @Column(name = "tracking_number", length = 255)
    private String trackingNumber;
}
