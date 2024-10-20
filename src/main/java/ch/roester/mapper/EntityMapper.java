package ch.roester.mapper;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface EntityMapper<REQ, RES, E> {

    E fromRequestDTO(REQ dto);

    RES toResponseDTO(E entity);

    Set<E> fromRequestDTO(Set<REQ> dtoList);

    Set<RES> toResponseDTO(Set<E> entityList);

    List<RES> toResponseDTO(List<E> entityList);

    default Page<RES> toResponseDTO(Page<E> entityPages) {
        return entityPages.map(this::toResponseDTO);
    }

}
