package ch.roester.product;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class ProductResponseDTO extends ProductRequestDTO {
    private Set<Integer> eventIds;
    private Set<Integer> sizePriceIds;
    private Set<Integer> tagIds;
    private Set<Integer> propertyIds;
    private Set<Integer> productOrderIds;
}