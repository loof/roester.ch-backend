package ch.roester.event_product_amount;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventProductAmountRequestDTO {
    private Integer id;
    private Integer event_id;
    private Integer product_id;
    private Double amountLeft;
    private Double amountTotal;
}
