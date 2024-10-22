package ch.roester.order;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PENDING("Pending"),
    PAID("Paid"),
    SHIPPED("Shipped"),
    CANCELLED("Cancelled"),
    DELIVERED("Delivered"),
    RETURNED("Returned");

    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
