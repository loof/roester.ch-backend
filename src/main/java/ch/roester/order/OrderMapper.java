package ch.roester.order;

import ch.roester.event.Event;
import ch.roester.event.EventRequestDTO;
import ch.roester.event.EventResponseDTO;
import ch.roester.location.LocationMapper;
import ch.roester.mapper.EntityMapper;
import ch.roester.product.Product;
import ch.roester.tag.Tag;
import ch.roester.tag.TagResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Mapper(componentModel = "spring", uses = {PositionMapper.class})
public interface OrderMapper extends EntityMapper<OrderRequestDTO, OrderResponseDTO, Order> {


}
