package ch.roester.event;

import ch.roester.location.LocationResponseDTO;
import ch.roester.product_event.ProductEventResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class EventResponseDTO extends EventRequestDTO {
    private LocationResponseDTO location;
    private List<ProductEventResponseDTO> productEvents;
}
