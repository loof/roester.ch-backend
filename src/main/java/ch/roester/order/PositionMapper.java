package ch.roester.order;

import ch.roester.location.LocationMapper;
import ch.roester.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface PositionMapper extends EntityMapper<PositionRequestDTO, PositionResponseDTO, Position> {

}
