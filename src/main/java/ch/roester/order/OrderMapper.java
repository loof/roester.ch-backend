package ch.roester.order;

import ch.roester.mapper.EntityMapper;
import ch.roester.shipment.ShipmentMapper;
import org.mapstruct.Mapper;
import org.springframework.aot.hint.SerializationHints;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {PositionMapper.class, ShipmentMapper.class})
public interface OrderMapper extends EntityMapper<OrderRequestDTO, OrderResponseDTO, Order> {

    Order fromRequestDTO(OrderRequestDTO orderRequestDTO);

    OrderResponseDTO toResponseDTO(Order order);

}
