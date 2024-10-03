package ch.roester.cart;

import ch.roester.event.EventRequestDTO;
import ch.roester.variant.VariantResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class CartItemResponseDTO extends CartItemRequestDTO {
    private VariantResponseDTO variant;
    private Integer cartId;
    private BigDecimal subTotal;
}
