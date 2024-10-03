package ch.roester.cart;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CartResponseDTO extends CartRequestDTO {
    private List<CartItemResponseDTO> items;
    private Integer userId;
    private BigDecimal total;
}
