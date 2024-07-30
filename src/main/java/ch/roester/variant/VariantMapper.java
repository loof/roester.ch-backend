package ch.roester.variant;

import ch.roester.mapper.EntityMapper;
import ch.roester.product.Product;
import ch.roester.tag.Tag;
import ch.roester.tag.TagRequestDTO;
import ch.roester.tag.TagResponseDTO;
import ch.roester.unit.UnitMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring", uses = UnitMapper.class)
public interface VariantMapper extends EntityMapper<VariantRequestDTO, VariantResponseDTO, Variant> {

    @Override
    @Mapping(target = "productId", source = "product", qualifiedByName = "productToProductId")
    VariantResponseDTO toResponseDTO(Variant variant);

    @Named("productToProductId")
    default Integer productToProductId(Product product) {
        return product.getId();
    }

}