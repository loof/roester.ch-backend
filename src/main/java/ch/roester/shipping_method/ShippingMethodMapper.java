package ch.roester.shipping_method;


import ch.roester.carrier.CarrierMapper;
import ch.roester.mapper.EntityMapper;
import ch.roester.shipment.Shipment;
import ch.roester.shipment.ShipmentRequestDTO;
import ch.roester.shipment.ShipmentResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CarrierMapper.class})
public interface ShippingMethodMapper extends EntityMapper<ShippingMethodRequestDTO, ShippingMethodResponseDTO, ShippingMethod> {

}
