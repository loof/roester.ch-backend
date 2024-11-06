package ch.roester.order;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PositionRequestDTO {
    @NotNull
    private Integer variantId;

    @NotNull
    private Integer amount;


    private Integer eventId;
}
