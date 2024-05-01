package ch.roester.property;

import ch.roester.mapper.EntityMapper;
import ch.roester.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface PropertyMapper extends EntityMapper<PropertyRequestDTO, PropertyResponseDTO, Property> {

    @Override
    @Mapping(target = "productIds", source = "products", qualifiedByName = "productsToProductIds")
    PropertyResponseDTO toResponseDTO(Property property);

    @Named("productsToProductIds")
    default Set<Integer> productsToProductIds(Set<Product> products) {
        Set<Integer> productIds = new HashSet<>();
        for (Product product : products) {
            if (product.getId() != null) {
                productIds.add(product.getId());
            }
        }
        return productIds;
    }

}