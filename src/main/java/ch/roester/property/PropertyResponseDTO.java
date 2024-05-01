package ch.roester.property;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class PropertyResponseDTO extends PropertyRequestDTO {

    private Set<Integer> productIds;

}
