package ch.roester.event;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventRequestDTO {
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime date;
}
