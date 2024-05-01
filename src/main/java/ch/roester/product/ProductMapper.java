package ch.roester.product;

import ch.roester.event.Event;
import ch.roester.mapper.EntityMapper;
import ch.roester.order.Order;
import ch.roester.product_event.ProductEvent;
import ch.roester.product_event_order.ProductEventOrder;
import ch.roester.product_order.ProductOrder;
import ch.roester.product_size_price.ProductSizePrice;
import ch.roester.property.Property;
import ch.roester.tag.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductRequestDTO, ProductResponseDTO, Product> {

    @Override
    @Mapping(target = "tagIds", source = "tags", qualifiedByName = "tagsToTagIds")
    @Mapping(target = "eventIds", source = "events", qualifiedByName = "eventsToEventIds")
    @Mapping(target = "sizePriceIds", source = "sizePrices", qualifiedByName = "sizePricesTosizePriceIds")
    @Mapping(target = "productOrderIds", source = "productOrders", qualifiedByName = "productOrdersToProductOrderIds")
    @Mapping(target = "propertyIds", source = "properties", qualifiedByName = "propertiesToPropertyIds")
    ProductResponseDTO toResponseDTO(Product product);

    @Named("eventsToEventIds")
    default Set<Integer> eventsToEventIds(Set<ProductEvent> events) {
        Set<Integer> eventIds = new HashSet<>();
        for (ProductEvent event : events) {
            eventIds.add(event.getId());
        }
        return eventIds;
    }

    @Named("tagsToTagIds")
    default Set<Integer> tagsToTagIds(Set<Tag> tags) {
        Set<Integer> tagIds = new HashSet<>();
        for (Tag tag : tags) {
            tagIds.add(tag.getId());
        }
        return tagIds;
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

    @Named("productEventOrdersToProductEventOrderIds")
    default Set<Integer> productEventOrdersToProductEventOrderIds(Set<ProductEventOrder> productOrders) {
        Set<Integer> productOrderIds = new HashSet<>();
        for (ProductEventOrder productOrder : productOrders) {
            productOrderIds.add(productOrder.getId());
        }
        return productOrderIds;
    }

    @Named("propertiesToPropertyIds")
    default Set<Integer> propertiesToPropertyIds(Set<Property> properties) {
        Set<Integer> propertyIds = new HashSet<>();
        for (Property property : properties) {
            propertyIds.add(property.getId());
        }
        return propertyIds;
    }
}