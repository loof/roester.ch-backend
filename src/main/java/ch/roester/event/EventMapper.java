package ch.roester.event;

import ch.roester.event_product_amount.EventProductAmount;
import ch.roester.event_product_amount.EventProductAmountMapper;
import ch.roester.location.Location;
import ch.roester.location.LocationMapper;
import ch.roester.mapper.EntityMapper;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@Mapper(componentModel = "spring", uses = {LocationMapper.class, EventProductAmountMapper.class})
public interface EventMapper extends EntityMapper<EventRequestDTO, EventResponseDTO, Event> {
    @Override
    @Mapping(target = "amountLeft", ignore = true)
    EventResponseDTO toResponseDTO(Event event);

    @Override
    @Mapping(target = "location", source = "locationId", qualifiedByName = "locationIdToLocation")
    Event fromRequestDTO(EventRequestDTO dto);

    @Named("locationIdToLocation")
    default Location locationIdToLocation(Integer locationId) {
        Location location = new Location();
        location.setId(locationId);
        return location;
    }

    @AfterMapping
    default void calculateAmountLeft(Event event, @MappingTarget EventResponseDTO dto) {
        double amountLeft = 0d;
        if (event.getEventProductAmounts() == null) {
            return;
        }
        for (EventProductAmount epa : event.getEventProductAmounts()) {
            if (epa.getAmountLeft() != null) {
                amountLeft += epa.getAmountLeft();
            }
        }
        dto.setAmountLeft(amountLeft);
    }

    @AfterMapping
    default void isReservationOpen(Event event, @MappingTarget EventResponseDTO dto) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime eventDate = event.getDate();
        LocalDateTime reservationEndDate = eventDate.minusDays(event.getDaysBeforeSubscriptionCloses());
        Duration duration = Duration.between(now, reservationEndDate);
        dto.setIsReservationOpen(duration.toDays() > 0);
    }

    @AfterMapping
    default void daysToEvent(Event event, @MappingTarget EventResponseDTO dto) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime eventDate = event.getDate();
        Duration duration = Duration.between(now, eventDate);
        dto.setDaysToEvent(duration.toDays());
    }
}
