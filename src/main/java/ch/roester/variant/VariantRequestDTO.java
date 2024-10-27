package ch.roester.variant;

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
    private Integer displayUnitId;
    private BigDecimal price;
    private BigDecimal stockMultiplier;
    private String separateShipmentComment;

    public VariantRequestDTO() {
    }

}