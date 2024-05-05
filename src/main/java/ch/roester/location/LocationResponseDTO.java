package ch.roester.location;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LocationResponseDTO extends LocationRequestDTO {
    private List<Integer> eventIds;
}
