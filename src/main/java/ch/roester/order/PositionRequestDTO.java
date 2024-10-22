package ch.roester.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PositionRequestDTO {
    private Integer id;
    private Integer variantId;
    private Integer amount;
    private Integer eventId;
}
