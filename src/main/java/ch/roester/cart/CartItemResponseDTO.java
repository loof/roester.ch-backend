package ch.roester.cart;

import ch.roester.variant.VariantResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CartItemResponseDTO extends CartItemRequestDTO {
    private VariantResponseDTO variant;
    private Integer cartId;
    private BigDecimal subTotal;
    private Double eventProductAmountLeft;
}
