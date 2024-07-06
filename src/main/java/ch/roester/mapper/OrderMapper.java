package ch.roester.mapper;

import ch.roester.dto.OrderDto;
import ch.roester.order.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper extends EntityMapper<OrderDto, Order> {
}