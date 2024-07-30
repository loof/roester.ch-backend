package ch.roester.product;

import ch.roester.part.PartResponseDTO;
import ch.roester.property.PropertyResponseDTO;
import ch.roester.tag.TagResponseDTO;
import ch.roester.variant.VariantResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Setter
@Getter
public class ProductResponseDTO extends ProductRequestDTO {
    private Set<TagResponseDTO> tags;
    private Set<PropertyResponseDTO> properties;
    private Set<PartResponseDTO> madeOf;
    private List<VariantResponseDTO> variants;
}