package ch.roester.order;

import ch.roester.event.Event;
import ch.roester.event.EventRequestDTO;
import ch.roester.event.EventResponseDTO;
import ch.roester.location.LocationMapper;
import ch.roester.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {LocationMapper.class})
public interface OrderMapper extends EntityMapper<EventRequestDTO, EventResponseDTO, Event> {

}
