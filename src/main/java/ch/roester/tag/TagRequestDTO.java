package ch.roester.tag;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;


@Setter
@Getter
public class TagRequestDTO {
    private Integer id;

    @NotBlank(message = "tag name is required and can't be empty")
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagRequestDTO that = (TagRequestDTO) o;
        return Objects.equals(name.toLowerCase(), that.name.toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    public TagRequestDTO() {
    }

}