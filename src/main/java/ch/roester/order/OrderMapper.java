package ch.roester.order;

import ch.roester.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {PositionMapper.class})
public interface OrderMapper extends EntityMapper<OrderRequestDTO, OrderResponseDTO, Order> {

    @Mappings({
            @Mapping(source = "appUserId", target = "appUser.id"), // Map appUserId to appUser ID
    })
    Order fromRequestDTO(OrderRequestDTO orderRequestDTO);

    @Mappings({
            @Mapping(source = "appUser.id", target = "appUserId"), // Map appUser ID to appUserId
    })
    OrderResponseDTO toResponseDTO(Order order);
}
