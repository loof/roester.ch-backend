package ch.roester.product;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ProductRequestDTO {
    private Integer id;
    private String name;
    private String description;

    public ProductRequestDTO() {
    }

}