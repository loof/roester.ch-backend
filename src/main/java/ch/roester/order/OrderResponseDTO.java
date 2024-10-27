package ch.roester.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderResponseDTO extends OrderRequestDTO {
    private Integer id;
    private BigDecimal totalCost;
    private BigDecimal totalShippingCost;
    private BigDecimal orderTotal;
    private int numberOfParcels;
}