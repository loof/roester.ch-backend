package ch.roester.tag;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class TagResponseDTO extends TagRequestDTO {

    private Set<Integer> productIds;



}