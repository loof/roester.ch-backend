package ch.roester.event;

import ch.roester.location.LocationMapper;
import ch.roester.mapper.EntityMapper;
import ch.roester.product_event.ProductEventMapper;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = {LocationMapper.class, ProductEventMapper.class})
public interface EventMapper extends EntityMapper<EventRequestDTO, EventResponseDTO, Event> {

}
