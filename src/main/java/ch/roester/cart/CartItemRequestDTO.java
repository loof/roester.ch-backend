package ch.roester.cart;

import ch.roester.variant.VariantRequestDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemRequestDTO {
    private Integer id;
    private Integer cartId;
    private Integer eventId;
    private Integer variantId;
    private Double amount;
}
