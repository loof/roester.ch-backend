package ch.roester.cart;

import ch.roester.event.Event;
import ch.roester.event_product_amount.EventProductAmount;
import ch.roester.mapper.EntityMapper;
import ch.roester.product.Product;
import ch.roester.variant.Variant;
import ch.roester.variant.VariantMapper;
import ch.roester.variant.VariantResponseDTO;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Component
@Mapper(componentModel = "spring", uses = VariantMapper.class)
public interface CartItemMapper extends EntityMapper<CartItemRequestDTO, CartItemResponseDTO, CartItem> {

    @Override
    @Mapping(target = "eventProductAmountId", source = "eventProductAmount", qualifiedByName = "eventProductAmountToEventProductAmountId")
    @Mapping(target = "variantId", source = "variant", qualifiedByName = "variantToVariantId")
    @Mapping(target = "cartId", source = "cart", qualifiedByName = "cartToCartId")
    CartItemResponseDTO toResponseDTO(CartItem cart);

    @Override
    @Mapping(target = "eventProductAmount", source = "eventProductAmountId", qualifiedByName = "eventProductAmountIdToEventProductAmount")
    @Mapping(target = "variant", source = "variantId", qualifiedByName = "variantIdToVariant")
    CartItem fromRequestDTO(CartItemRequestDTO dto);

    @Named("eventProductAmountToEventProductAmountId")
    default Integer eventProductAmountToEventProductAmountId(EventProductAmount eventProductAmount) {
        if (eventProductAmount != null) {
            return eventProductAmount.getId();
        }
        return null;
    }

    @Named("eventProductAmountIdToEventProductAmount")
    default EventProductAmount eventProductAmountIdToEventProductAmount(Integer eventProductAmountId) {
        EventProductAmount eventProductAmount = new EventProductAmount();
        if (eventProductAmountId != null) {
            eventProductAmount.setId(eventProductAmountId);
        }
        return eventProductAmount;
    }

    @Named("variantIdToVariant")
    default Variant variantIdToVariant(Integer variantId) {
        Variant variant = new Variant();
        if (variantId != null) {
            variant.setId(variantId);
        }
        return variant;
    }

    @Named("cartIdToCart")
    default Cart cartIdToCart(Integer cartId) {
        Cart cart = new Cart();
        if (cartId != null) {
            cart.setId(cartId);
        }
        return cart;
    }

    @Named("variantToVariantId")
    default Integer variantToVariantId(Variant variant) {
        return variant.getId();
    }

    @Named("cartToCartId")
    default Integer cartToCartId(Cart cart) {
        return cart.getId();
    }

    @AfterMapping
    default void calculateSubTotal(CartItem cartItem, @MappingTarget CartItemResponseDTO dto) {
        dto.setSubTotal(cartItem.getVariant().getStockMultiplier().multiply(cartItem.getVariant().getProduct().getPricePerUnit()).multiply(BigDecimal.valueOf(dto.getAmount())));
    }
}
