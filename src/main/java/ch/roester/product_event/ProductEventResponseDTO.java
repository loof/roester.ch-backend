package ch.roester.product_event;

import ch.roester.product.ProductResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductEventResponseDTO extends ProductEventRequestDTO {
    private ProductResponseDTO product;
    private String amountAvailableUnit;
}
