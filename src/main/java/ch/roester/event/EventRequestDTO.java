package ch.roester.event;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventRequestDTO {
    private Integer id;
    private String name;
    private String description;

    @NotNull
    private Integer locationId;

    @NotNull
    private LocalDateTime date;

    @NotNull
    private Integer daysBeforeSubscriptionCloses;
}
