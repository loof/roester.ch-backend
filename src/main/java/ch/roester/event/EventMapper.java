package ch.roester.event;

import ch.roester.location.LocationMapper;
import ch.roester.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {LocationMapper.class})
public interface EventMapper extends EntityMapper<EventRequestDTO, EventResponseDTO, Event> {

}
