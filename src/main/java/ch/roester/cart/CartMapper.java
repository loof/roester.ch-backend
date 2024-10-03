package ch.roester.cart;

import ch.roester.app_user.AppUser;
import ch.roester.mapper.EntityMapper;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

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

    @AfterMapping
    default void calculateTotal(Cart cart, @MappingTarget CartResponseDTO dto) {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItemResponseDTO item : dto.getItems()) {
            total = total.add(item.getSubTotal());
        }
        dto.setTotal(total);
    }

}
