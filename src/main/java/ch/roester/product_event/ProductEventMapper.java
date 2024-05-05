package ch.roester.product_event;

import ch.roester.event.Event;
import ch.roester.mapper.EntityMapper;
import ch.roester.product.Product;
import ch.roester.product.ProductMapper;
import ch.roester.unit.Unit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface ProductEventMapper extends EntityMapper<ProductEventRequestDTO, ProductEventResponseDTO, ProductEvent> {
    @Override
    @Mapping(target = "eventId", source = "event", qualifiedByName = "eventToEventId")
    @Mapping(target = "amountAvailableUnit", source = "amountAvailableUnit", qualifiedByName = "fromEntityUnitToDtoUnit")
    @Mapping(target = "productId", source = "product", qualifiedByName = "fromProductToProductId")
    ProductEventResponseDTO toResponseDTO(ProductEvent productEvent);

    @Named("fromProductToProductId")
    default Integer fromProductToProductId(Product product) {
        return product.getId();
    }

    @Named("eventToEventId")
    default Integer eventToEventId(Event event) {
        return event.getId();
    }

    @Named("fromEntityUnitToDtoUnit")
    default String unitToUnit(Unit unit) {
        return unit.getName();
    }

    @Named("fromDtoUnitToEntityUnit")
    default Unit unitToUnit(String unitName) {
        Unit unit = new Unit();
        unit.setName(unitName);
        return unit;
    }

}
