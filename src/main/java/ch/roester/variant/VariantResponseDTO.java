package ch.roester.variant;

import ch.roester.tag.TagRequestDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class VariantResponseDTO extends VariantRequestDTO {
    private String productName;
}