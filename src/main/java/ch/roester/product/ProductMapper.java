package ch.roester.product;

import ch.roester.mapper.EntityMapper;
import ch.roester.product_event.ProductEvent;
import ch.roester.product_size_price.ProductSizePrice;
import ch.roester.product_size_price.ProductSizePriceMapper;
import ch.roester.property.PropertyMapper;
import ch.roester.tag.TagMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Mapper(componentModel = "spring", uses = {PropertyMapper.class, TagMapper.class, ProductSizePriceMapper.class})
public interface ProductMapper extends EntityMapper<ProductRequestDTO, ProductResponseDTO, Product> {

    @Override
    @Mapping(target = "eventIds", source = "productEvents", qualifiedByName = "eventsToEventIds")
    ProductResponseDTO toResponseDTO(Product product);

    @Named("eventsToEventIds")
    default List<Integer> eventsToEventIds(List<ProductEvent> events) {
        List<Integer> eventIds = new ArrayList<>();
        for (ProductEvent event : events) {
            eventIds.add(event.getId());
        }
        return eventIds;
    }


    @Named("fromSizePricesToSizes")
    default List<Integer> fromSizePricesToSizes(List<ProductSizePrice> sizePrices) {
        List<Integer> sizePriceIds = new ArrayList<>();
        for (ProductSizePrice sizePrice : sizePrices) {
            sizePriceIds.add(sizePrice.getId());
        }
        return sizePriceIds;
    }
}