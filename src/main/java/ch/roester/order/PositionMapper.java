package ch.roester.order;

import ch.roester.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface PositionMapper extends EntityMapper<PositionRequestDTO, PositionResponseDTO, Position> {

    @Mapping(target = "variant.id", source = "variantId") // Map variantId to variant ID in Position entity
    @Mapping(target = "event.id", source = "eventId") // Map eventId to event ID in Position entity
    Position fromRequestDTO(PositionRequestDTO positionRequestDTO);

    @Mapping(target = "variantId", source = "variant.id") // Map variant ID back to DTO
    @Mapping(target = "eventId", source = "event.id") // Map event ID back to DTO
    PositionResponseDTO toResponseDTO(Position position);
}
