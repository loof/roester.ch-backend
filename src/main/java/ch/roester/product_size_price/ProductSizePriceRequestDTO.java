package ch.roester.product_size_price;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSizePriceRequestDTO {
    private Integer id;

    @NotNull
    private String unit;

    @NotNull
    private Double price;

    @NotNull
    private Double amount;
}
