package ch.roester.shipment;

import ch.roester.carrier.Carrier;
import ch.roester.mapper.EntityMapper;
import ch.roester.order.Order;
import ch.roester.shipping_method.ShippingMethod;
import ch.roester.shipping_method.ShippingMethodMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Mapper(componentModel = "spring", uses = {ShippingMethodMapper.class})
public interface ShipmentMapper extends EntityMapper<ShipmentRequestDTO, ShipmentResponseDTO, Shipment> {

    @Mapping(target = "shippingMethod.id", source = "shippingMethodId") // Map shippingMethodId to ShippingMethod ID in Shipment entity
    @Mapping(target = "order.id", source = "orderId") // Map orderId to Order ID in Shipment entity
    Shipment fromRequestDTO(ShipmentRequestDTO shipmentRequestDTO);

    @Mapping(target = "shippingMethodId", source = "shippingMethod.id") // Map ShippingMethod ID back to DTO
    @Mapping(target = "orderId", source = "order.id") // Map Order ID back to DTO
    ShipmentResponseDTO toResponseDTO(Shipment shipment);

    default List<Shipment> convertShipmentDTOsToEntities(List<ShipmentResponseDTO> shipmentDTOs, Integer orderId) {
        List<Shipment> shipments = new ArrayList<>();
        for (ShipmentResponseDTO shipmentDTO : shipmentDTOs) {
            Shipment shipment = new Shipment();
            shipment.setShipmentCost(shipmentDTO.getShipmentCost());
            ShippingMethod shippingMethod = new ShippingMethod();
            Carrier carrier = new Carrier();
            carrier.setName(shipmentDTO.getShippingMethod().getCarrier().getName());
            carrier.setWebsite(shipmentDTO.getShippingMethod().getCarrier().getWebsite());
            carrier.setEmail(shipmentDTO.getShippingMethod().getCarrier().getEmail());
            carrier.setContactNumber(shipmentDTO.getShippingMethod().getCarrier().getContactNumber());
            carrier.setId(shipmentDTO.getShippingMethod().getCarrier().getId());
            shippingMethod.setCarrier(carrier);
            shippingMethod.setId(shipmentDTO.getShippingMethod().getId());
            shipment.setShippingMethod(shippingMethod);
            Order order = new Order();
            order.setId(orderId);
            shipment.setOrder(order);
            shipments.add(shipment);
        }
        return shipments;
    }
}