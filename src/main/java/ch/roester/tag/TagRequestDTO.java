package ch.roester.tag;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class TagRequestDTO {
    private Integer id;
    private String name;


    public TagRequestDTO() {
    }

}