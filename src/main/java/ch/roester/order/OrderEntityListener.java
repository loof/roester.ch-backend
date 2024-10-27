package ch.roester.order;

import ch.roester.variant.Variant;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.math.BigDecimal;

public class OrderEntityListener {

    @PrePersist
    @PreUpdate
    public void calculateTotalCost(Order order) {
        BigDecimal totalCost = BigDecimal.ZERO;
        if (order.getPositions() != null) {
            for (Position position : order.getPositions()) {
                Variant variant = position.getVariant();
                BigDecimal positionCost = variant.getStockMultiplier()
                        .multiply(variant.getProduct().getPricePerUnit())
                        .multiply(new BigDecimal(position.getAmount()));
                totalCost = totalCost.add(positionCost);
            }
        }
        order.setTotalCost(totalCost);
    }
}