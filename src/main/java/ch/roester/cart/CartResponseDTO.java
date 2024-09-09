package ch.roester.cart;

import ch.roester.event.EventRequestDTO;
import ch.roester.event_product_amount.EventProductAmountResponseDTO;
import ch.roester.location.LocationResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartResponseDTO extends CartRequestDTO {
    private List<CartItemResponseDTO> items;
    private Integer userId;
}
