package ch.roester.shipping_method;


import ch.roester.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShippingMethodMapper extends EntityMapper<ShippingMethodRequestDTO, ShippingMethodResponseDTO, ShippingMethod> {

}
