package ch.roester.stock;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockRequestDTO {
    private Integer locationId;  // Refers to Location entity
}