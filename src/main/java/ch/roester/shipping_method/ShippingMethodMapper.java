package ch.roester.shipping_method;


import ch.roester.event.Event;
import ch.roester.location.Location;
import ch.roester.location.LocationRequestDTO;
import ch.roester.location.LocationResponseDTO;
import ch.roester.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ShippingMethodMapper extends EntityMapper<ShippingMethodRequestDTO, ShippingMethodResponseDTO, ShippingMethod> {

}
