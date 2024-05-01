package ch.roester.tag;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
public class TagRequestDTO {
    private Integer id;
    private String name;


    public TagRequestDTO() {
    }

}