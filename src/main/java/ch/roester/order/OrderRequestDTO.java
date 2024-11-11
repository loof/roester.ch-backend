package ch.roester.order;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestDTO {

    @NotNull
    private List<PositionRequestDTO> positions;

    private Integer carrierId;

}
