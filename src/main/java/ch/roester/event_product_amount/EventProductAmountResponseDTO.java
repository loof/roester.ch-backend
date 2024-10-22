package ch.roester.event_product_amount;

import ch.roester.product.ProductResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventProductAmountResponseDTO extends EventProductAmountRequestDTO {
    private ProductResponseDTO product;
}
