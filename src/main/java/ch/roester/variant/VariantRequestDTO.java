package ch.roester.variant;

import ch.roester.unit.UnitRequestDTO;
import ch.roester.unit.UnitResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Setter
@Getter
public class VariantRequestDTO {
    private Integer id;
    private String name;
    private String description;
    private Integer productId;
    private UnitRequestDTO unit;
    private BigDecimal price;

    public VariantRequestDTO() {
    }

}