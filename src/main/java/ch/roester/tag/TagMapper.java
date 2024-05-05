package ch.roester.tag;

import ch.roester.mapper.EntityMapper;
import ch.roester.product.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface TagMapper extends EntityMapper<TagRequestDTO, TagResponseDTO, Tag> {

    @Override
    @Mapping(target = "productIds", source = "products", qualifiedByName = "productsToProductIds")
    TagResponseDTO toResponseDTO(Tag tag);

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