package ch.roester.event;

import ch.roester.event_product_amount.EventProductAmount;
import ch.roester.event_product_amount.EventProductAmountMapper;
import ch.roester.location.LocationMapper;
import ch.roester.mapper.EntityMapper;
import ch.roester.product.ProductMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {LocationMapper.class, EventProductAmountMapper.class})
public interface EventMapper extends EntityMapper<EventRequestDTO, EventResponseDTO, Event> {
    @Override
    @Mapping(target = "amountLeft", ignore = true)
    EventResponseDTO toResponseDTO(Event event);

    @AfterMapping // or @BeforeMapping
    default void calculateamountLeft(Event event, @MappingTarget EventResponseDTO dto) {
        Double amountLeft = 0d;
        for (EventProductAmount epa : event.getEventProductAmounts()) {
            if (epa.getAmountLeft() != null) {
                amountLeft += epa.getAmountLeft();
            }

        }
        dto.setAmountLeft(amountLeft);
    }
}
