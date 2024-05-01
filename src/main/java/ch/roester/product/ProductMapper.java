package ch.roester.product;

import ch.roester.mapper.EntityMapper;
import ch.roester.product_event.ProductEvent;
import ch.roester.product_order.ProductOrder;
import ch.roester.product_size_price.ProductSizePrice;
import ch.roester.property.PropertyMapper;
import ch.roester.tag.TagMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {PropertyMapper.class, TagMapper.class})
public interface ProductMapper extends EntityMapper<ProductRequestDTO, ProductResponseDTO, Product> {

    @Override
    @Mapping(target = "eventIds", source = "events", qualifiedByName = "eventsToEventIds")
    @Mapping(target = "sizePriceIds", source = "sizePrices", qualifiedByName = "sizePricesTosizePriceIds")
    @Mapping(target = "productOrderIds", source = "orders", qualifiedByName = "productOrdersToProductOrderIds")
    ProductResponseDTO toResponseDTO(Product product);

    @Named("eventsToEventIds")
    default Set<Integer> eventsToEventIds(Set<ProductEvent> events) {
        Set<Integer> eventIds = new HashSet<>();
        for (ProductEvent event : events) {
            eventIds.add(event.getId());
        }
        return eventIds;
    }

    @Named("sizePricesTosizePriceIds")
    default Set<Integer> sizePricesTosizePriceIds(Set<ProductSizePrice> sizePrices) {
        Set<Integer> sizePriceIds = new HashSet<>();
        for (ProductSizePrice sizePrice : sizePrices) {
            sizePriceIds.add(sizePrice.getId());
        }
        return sizePriceIds;
    }

    @Named("productOrdersToProductOrderIds")
    default Set<Integer> productOrdersToProductOrderIds(Set<ProductOrder> productOrders) {
        Set<Integer> productOrderIds = new HashSet<>();
        for (ProductOrder productOrder : productOrders) {
            productOrderIds.add(productOrder.getId());
        }
        return productOrderIds;
    }

}