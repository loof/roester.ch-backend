package ch.roester.order;

import ch.roester.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {PositionMapper.class})
public interface OrderMapper extends EntityMapper<OrderRequestDTO, OrderResponseDTO, Order> {


}
