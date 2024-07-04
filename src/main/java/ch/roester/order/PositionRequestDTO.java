package ch.roester.order;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PositionRequestDTO {
    private Integer id;
    private Integer variantId;
    private Integer amount;
}
