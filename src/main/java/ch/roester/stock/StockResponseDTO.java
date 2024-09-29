package ch.roester.stock;

import ch.roester.location.LocationResponseDTO;
import ch.roester.product.ProductResponseDTO;
import ch.roester.variant.VariantResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StockResponseDTO extends StockRequestDTO {
    private Integer id;  // Stock ID
    private LocationResponseDTO location;  // Refers to Location entity
    private List<ProductResponseDTO> products;  // List of product ids
    private List<VariantResponseDTO> variants;  // List of variant ids
}