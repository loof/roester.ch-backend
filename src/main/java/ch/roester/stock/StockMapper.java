package ch.roester.stock;

import ch.roester.location.Location;
import ch.roester.location.LocationMapper;
import ch.roester.mapper.EntityMapper;
import ch.roester.product.Product;
import ch.roester.product.ProductMapper;
import ch.roester.variant.Variant;
import ch.roester.variant.VariantMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {LocationMapper.class, ProductMapper.class, VariantMapper.class})
public interface StockMapper extends EntityMapper<StockRequestDTO, StockResponseDTO, Stock> {

    @Override
    @Mapping(target = "location", source = "locationId", qualifiedByName = "locationIdToLocation")
    Stock fromRequestDTO(StockRequestDTO dto);

    @Named("locationIdToLocation")
    default Location locationIdToLocation(Integer locationId) {
        if (locationId == null) {
            return null; // Return null if locationId is not provided
        }
        Location location = new Location();
        location.setId(locationId);
        return location; // Create a Location entity with the provided ID
    }

}
