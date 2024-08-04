package ch.roester.order;

import ch.roester.event.Event;
import ch.roester.location.LocationMapper;
import ch.roester.mapper.EntityMapper;
import ch.roester.product.Product;
import ch.roester.variant.Variant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Mapper(componentModel = "spring")
public interface PositionMapper extends EntityMapper<PositionRequestDTO, PositionResponseDTO, Position> {
    @Override
    @Mapping(target = "eventId", source = "event", qualifiedByName = "eventToEventId")
    @Mapping(target = "variantId", source = "variant", qualifiedByName = "variantToVariantId")
    PositionResponseDTO toResponseDTO(Position position);

    @Named("eventToEventId")
    default Integer eventToEventId(Event event) {
        return event.getId();
    }

    @Named("variantToVariantId")
    default Integer variantToVariantId(Variant variant) {
        return variant.getId();
    }
}
