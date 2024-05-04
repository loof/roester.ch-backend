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
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Mapper(componentModel = "spring", uses = {PropertyMapper.class, TagMapper.class})
public interface ProductMapper extends EntityMapper<ProductRequestDTO, ProductResponseDTO, Product> {

    @Override
    @Mapping(target = "eventIds", source = "events", qualifiedByName = "eventsToEventIds")
    @Mapping(target = "sizePriceIds", source = "sizePrices", qualifiedByName = "sizePricesTosizePriceIds")
    @Mapping(target = "productOrderIds", source = "orders", qualifiedByName = "productOrdersToProductOrderIds")
    ProductResponseDTO toResponseDTO(Product product);

    @Named("eventsToEventIds")
    default List<Integer> eventsToEventIds(List<ProductEvent> events) {
        List<Integer> eventIds = new ArrayList<>();
        for (ProductEvent event : events) {
            eventIds.add(event.getId());
        }
        return eventIds;
    }

    @Named("sizePricesTosizePriceIds")
    default List<Integer> sizePricesTosizePriceIds(List<ProductSizePrice> sizePrices) {
        List<Integer> sizePriceIds = new ArrayList<>();
        for (ProductSizePrice sizePrice : sizePrices) {
            sizePriceIds.add(sizePrice.getId());
        }
        return sizePriceIds;
    }

    @Named("productOrdersToProductOrderIds")
    default List<Integer> productOrdersToProductOrderIds(List<ProductOrder> productOrders) {
        List<Integer> productOrderIds = new ArrayList<>();
        for (ProductOrder productOrder : productOrders) {
            productOrderIds.add(productOrder.getId());
        }
        return productOrderIds;
    }

}