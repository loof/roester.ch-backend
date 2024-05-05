package ch.roester.product;

import ch.roester.product_size_price.ProductSizePriceResponseDTO;
import ch.roester.property.PropertyResponseDTO;
import ch.roester.tag.TagResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Setter
@Getter
public class ProductResponseDTO extends ProductRequestDTO {
    private List<Integer> eventIds;
    private List<ProductSizePriceResponseDTO> sizePrices;
    private Set<TagResponseDTO> tags;
    private Set<PropertyResponseDTO> properties;
}