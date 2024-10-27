package ch.roester.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestDTO {
    private Integer appUserId;
    private List<PositionRequestDTO> positions;
}
