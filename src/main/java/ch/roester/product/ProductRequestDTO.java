package ch.roester.product;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ProductRequestDTO {
    private Integer id;

    @NotBlank(message = "product name is required and can't be empty")
    private String name;
    private String description;

    public ProductRequestDTO() {
    }

}