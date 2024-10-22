package ch.roester.shipment;

import ch.roester.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ShipmentMapper extends EntityMapper<ShipmentRequestDTO, ShipmentResponseDTO, Shipment> {


}
