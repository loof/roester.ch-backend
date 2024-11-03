package ch.roester.order;

import ch.roester.shipment.Shipment;
import ch.roester.shipment.ShipmentResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderResponseDTO extends OrderRequestDTO {
    private Integer id;
    private BigDecimal totalCost;
    private BigDecimal totalShippingCost;
    private BigDecimal orderTotal;
    private int numberOfParcels;
    private List<ShipmentResponseDTO> shipments;
}