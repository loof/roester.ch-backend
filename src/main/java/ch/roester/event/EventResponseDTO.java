package ch.roester.event;

import ch.roester.event_product_amount.EventProductAmount;
import ch.roester.event_product_amount.EventProductAmountResponseDTO;
import ch.roester.location.LocationResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class EventResponseDTO extends EventRequestDTO {
    private LocationResponseDTO location;
    private List<EventProductAmountResponseDTO> eventProductAmounts;
    private Double amountLeft;
    private Boolean isReservationOpen;
    private Long daysToEvent;
}
