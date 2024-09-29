package ch.roester.stock;

import ch.roester.product.ProductResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StockRequestDTO {
    private Integer locationId;  // Refers to Location entity
}