package ch.roester.order;

import lombok.Getter;

@Getter
public enum OrderStatus {
    WAITING_FOR_PAYMENT("Waiting for payment"),
    PAID("Paid"),
    SHIPPED("Shipped"),
    PARTIALLY_SHIPPED("Partially shipped"),
    CANCELLED("Cancelled"),
    DELIVERED("Delivered"),
    RETURNED("Returned"),
    PREPARING_SHIPMENT("Preparing Shipment");

    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
