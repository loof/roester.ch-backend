package ch.roester.part;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class PartRequestDTO {
    private Integer id;
    private Integer partId;
    private Integer productId;
    private Double amount;
    private Integer unitId;

}