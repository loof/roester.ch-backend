package ch.roester.product;

import ch.roester.property.PropertyResponseDTO;
import ch.roester.tag.TagResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class ProductResponseDTO extends ProductRequestDTO {
    private Set<Integer> eventIds;
    private Set<Integer> sizePriceIds;
    private Set<TagResponseDTO> tags;
    private Set<PropertyResponseDTO> properties;
    private Set<Integer> productOrderIds;
}