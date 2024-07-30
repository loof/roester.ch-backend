package ch.roester.part;

import ch.roester.product.ProductResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class PartResponseDTO extends PartRequestDTO {
    private ProductResponseDTO part;
    private Double amount;
}