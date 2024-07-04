package ch.roester.event_product_amount;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventProductAmountRequestDTO {
    private Integer id;
    private Integer event_id;
    private Integer product_id;
}
