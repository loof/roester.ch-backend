package ch.roester.event_product_amount;

import ch.roester.event.Event;
import ch.roester.event.EventRequestDTO;
import ch.roester.event.EventResponseDTO;
import ch.roester.location.Location;
import ch.roester.location.LocationMapper;
import ch.roester.location.LocationResponseDTO;
import ch.roester.mapper.EntityMapper;
import ch.roester.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface EventProductAmountMapper extends EntityMapper<EventProductAmountRequestDTO, EventProductAmountResponseDTO, EventProductAmount> {
    @Override
    @Mapping(target = "product_id", source = "product", qualifiedByName = "productToProductId")
    @Mapping(target = "event_id", source = "event", qualifiedByName = "eventToEventId")
    EventProductAmountResponseDTO toResponseDTO(EventProductAmount eventProductAmount);

    @Named("productToProductId")
    default Integer productToProductId(Product product) {
        return product.getId();
    }

    @Named("eventToEventId")
    default Integer eventToEventId(Event event) {
        return event.getId();
    }
}
