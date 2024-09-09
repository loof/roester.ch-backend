package ch.roester.cart;

import ch.roester.event.Event;
import ch.roester.mapper.EntityMapper;
import ch.roester.product.Product;
import ch.roester.variant.Variant;
import ch.roester.variant.VariantMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Mapper(componentModel = "spring", uses = VariantMapper.class)
public interface CartItemMapper extends EntityMapper<CartItemRequestDTO, CartItemResponseDTO, CartItem> {

    @Override
    @Mapping(target = "eventId", source = "event", qualifiedByName = "eventToEventId")
    @Mapping(target = "variantId", source = "variant", qualifiedByName = "variantToVariantId")
    @Mapping(target = "cartId", source = "cart", qualifiedByName = "cartToCartId")
    CartItemResponseDTO toResponseDTO(CartItem cart);

    @Named("eventToEventId")
    default Integer eventToEventId(Event event) {
        return event.getId();
    }

    @Named("variantToVariantId")
    default Integer variantToVariantId(Variant variant) {
        return variant.getId();
    }

    @Named("cartToCartId")
    default Integer cartToCartId(Cart cart) {
        return cart.getId();
    }

}
