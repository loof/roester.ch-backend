package ch.roester.cart;

import ch.roester.app_user.AppUser;
import ch.roester.event.Event;
import ch.roester.event.EventRequestDTO;
import ch.roester.event.EventResponseDTO;
import ch.roester.event_product_amount.EventProductAmount;
import ch.roester.event_product_amount.EventProductAmountMapper;
import ch.roester.location.LocationMapper;
import ch.roester.mapper.EntityMapper;
import ch.roester.variant.Variant;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@Mapper(componentModel = "spring", uses = CartItemMapper.class)
public interface CartMapper extends EntityMapper<CartRequestDTO, CartResponseDTO, Cart> {

    @Override
    @Mapping(target = "userId", source = "user", qualifiedByName = "userToUserId")
    CartResponseDTO toResponseDTO(Cart cart);

    @Named("userToUserId")
    default Integer userToUserId(AppUser user) {
        return user.getId();
    }

}
