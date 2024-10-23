package ch.roester.order;

import ch.roester.mapper.EntityMapper;
import org.mapstruct.Mapper;


import java.util.List;

@Mapper(componentModel = "spring")
public interface StatusMapper extends EntityMapper<StatusRequestDTO, StatusResponseDTO, Status> {

    Status fromRequestDTO(StatusRequestDTO statusRequestDTO);

    StatusResponseDTO toResponseDTO(Status status);
}
