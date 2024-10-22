package ch.roester.variant;

import ch.roester.unit.UnitResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VariantResponseDTO extends VariantRequestDTO {
    private String productName;
    private UnitResponseDTO displayUnit;
}