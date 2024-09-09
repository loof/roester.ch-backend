package ch.roester.cart;

import ch.roester.event.EventRequestDTO;
import ch.roester.variant.VariantResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartItemResponseDTO extends CartItemRequestDTO {
    private VariantResponseDTO variant;
}
