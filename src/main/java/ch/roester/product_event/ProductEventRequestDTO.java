package ch.roester.product_event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductEventRequestDTO {
    private Integer id;
    private Integer productId;
    private Integer eventId;
    private Integer amountAvailable;
    private Integer amountAvailableUnitId;
}
