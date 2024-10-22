package ch.roester.shipment;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ShipmentRequestDTO {
    private Integer orderId;           // Reference to Order ID
    private Integer shippingMethodId;  // Reference to ShippingMethod ID
    private Integer carrierId;         // Reference to Carrier ID
    private BigDecimal shipmentCost;   // Shipment cost
    private LocalDate shipmentDate;    // Shipment date
    private LocalDate deliveryDate;    // Delivery date
    private String trackingNumber;      // Tracking number
}