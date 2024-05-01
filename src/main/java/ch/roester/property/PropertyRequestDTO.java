package ch.roester.property;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PropertyRequestDTO {
    private Integer id;
    private String name;
    private String description;

    public PropertyRequestDTO() {}
}
