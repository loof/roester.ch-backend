package ch.roester.shipment;

import ch.roester.mapper.EntityMapper;
import ch.roester.shipping_method.ShippingMethodMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {ShippingMethodMapper.class})
public interface ShipmentMapper extends EntityMapper<ShipmentRequestDTO, ShipmentResponseDTO, Shipment> {

}
