package ch.roester.event_product_amount;

import ch.roester.event.Event;
import ch.roester.event.EventRequestDTO;
import ch.roester.event.EventResponseDTO;
import ch.roester.location.Location;
import ch.roester.location.LocationMapper;
import ch.roester.location.LocationResponseDTO;
import ch.roester.mapper.EntityMapper;
import ch.roester.product.Product;
import ch.roester.product.ProductMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface EventProductAmountMapper extends EntityMapper<EventProductAmountRequestDTO, EventProductAmountResponseDTO, EventProductAmount> {
    @Override
    @Mapping(target = "eventId", source = "event", qualifiedByName = "eventToEventId")
    @Mapping(target = "productId", source = "product", qualifiedByName = "productToProductId")
    EventProductAmountResponseDTO toResponseDTO(EventProductAmount eventProductAmount);

    @Override
    @Mapping(target = "event", source = "eventId", qualifiedByName = "eventIdToEvent")
    @Mapping(target = "product", source = "productId", qualifiedByName = "productIdToProduct")
    EventProductAmount fromRequestDTO(EventProductAmountRequestDTO dto);

    @Named("eventIdToEvent")
    default Event eventIdToEvent(Integer eventId) {
        Event event = new Event();
        event.setId(eventId);
        return event;
    }

    @Named("productIdToProduct")
    default Product productIdToProduct(Integer productId) {
        Product product = new Product();
        product.setId(productId);
        return product;
    }

    @Named("productToProductId")
    default Integer productToProductId(Product product) {
        return product.getId();
    }

    @Named("eventToEventId")
    default Integer eventToEventId(Event event) {
        return event.getId();
    }
}
