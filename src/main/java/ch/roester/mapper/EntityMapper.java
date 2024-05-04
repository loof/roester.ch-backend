package ch.roester.mapper;

import ch.qos.logback.core.pattern.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

public interface EntityMapper<REQ, RES, E> {

    E fromRequestDTO(REQ dto);

    RES toResponseDTO(E entity);

    Set<E> fromRequestDTO(Set<REQ> dtoList);

    Set<RES> toResponseDTO(Set<E> entityList);

    List<RES> toResponseDTO(List<E> entityList);

    default Page<RES> toResponseDTO(Page<E> entityPage) {
        return entityPage.map(new Function<E, RES>() {
            @Override
            public RES apply(E entity) {
                return toResponseDTO(entity);
            }
        });
    }

}
