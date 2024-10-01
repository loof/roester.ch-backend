package ch.roester.product;

import ch.roester.unit.UnitResponseDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Setter
@Getter
public class ProductRequestDTO {
    private Integer id;

    @NotBlank(message = "product name is required and can't be empty")
    private String name;
    private String description;
    private BigDecimal pricePerUnit;
    private Integer soldUnitId;
    private Double amountInStock;
    private Integer stockId;
    private Set<Integer> tagIds = new HashSet<>();

    public ProductRequestDTO() {
    }

}