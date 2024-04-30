package ch.roester.tag;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
public class TagRequestDTO {
    private Integer id;
    private String name;
    private List<Integer> coffee_ids;

    public TagRequestDTO() {
    }

}