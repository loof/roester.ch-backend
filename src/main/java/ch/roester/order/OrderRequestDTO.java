package ch.roester.order;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderRequestDTO {
    private Integer id;
    private Integer appUserId;
    private List<PositionRequestDTO> positions;
}
