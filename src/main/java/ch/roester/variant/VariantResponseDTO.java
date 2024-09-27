package ch.roester.variant;

import ch.roester.tag.TagRequestDTO;
import ch.roester.unit.Unit;
import ch.roester.unit.UnitResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class VariantResponseDTO extends VariantRequestDTO {
    private String productName;
    private UnitResponseDTO displayUnit;
}