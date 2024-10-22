package ch.roester.carrier;

import ch.roester.mapper.EntityMapper;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CarrierMapper extends EntityMapper<CarrierRequestDTO, CarrierResponseDTO, Carrier> {

    // Additional mapping methods can be defined here if needed
}
