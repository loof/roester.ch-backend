package ch.roester.shipping_method;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ShippingMethodRequestDTO {
    private Integer id;

    @NotBlank(message = "Shipping method name must be provided")
    private String name;

    private BigDecimal price;

    private String estimatedDeliveryTime;

    private String description;

}
