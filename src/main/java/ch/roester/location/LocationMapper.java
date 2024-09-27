package ch.roester.location;


import ch.roester.event.Event;
import ch.roester.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface LocationMapper extends EntityMapper<LocationRequestDTO, LocationResponseDTO, Location> {

    @Override
    @Mapping(target = "eventIds", source = "events", qualifiedByName = "eventsToEventIds")
    LocationResponseDTO toResponseDTO(Location location);

    @Named("eventsToEventIds")
    default List<Integer> eventsToEventIds(List<Event> events) {
        List<Integer> eventIds = new ArrayList<>();
        if (events == null) {
            return eventIds;
        }
        for (Event event : events) {
            if (event.getId() != null) {
                eventIds.add(event.getId());
            }
        }
        return eventIds;
    }
}
