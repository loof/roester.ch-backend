package ch.roester.mapper;

import java.util.List;
import java.util.Set;

public interface EntityMapper<REQ, RES, E> {

    E fromRequestDTO(REQ dto);

    RES toResponseDTO(E entity);

    List<E> fromRequestDTO(List<REQ> dtoList);

    List<RES> toResponseDTO(List<E> entityList);

    Set<RES> toResponseDTO(Set<E> entityList);
}
