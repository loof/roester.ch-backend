package ch.roester.part;

import ch.roester.product.ProductResponseDTO;
import ch.roester.unit.UnitResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class PartResponseDTO extends PartRequestDTO {
    private UnitResponseDTO unit;
    private ProductResponseDTO part;
}