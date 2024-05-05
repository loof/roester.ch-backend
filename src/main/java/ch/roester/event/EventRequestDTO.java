package ch.roester.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventRequestDTO {
    private Integer id;
    private String name;
    private String description;
}
