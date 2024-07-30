package ch.roester.variant;

import ch.roester.mapper.EntityMapper;
import ch.roester.product.Product;
import ch.roester.unit.UnitMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = UnitMapper.class)
public interface VariantMapper extends EntityMapper<VariantRequestDTO, VariantResponseDTO, Variant> {

    @Override
    @Mapping(target = "productId", source = "product", qualifiedByName = "productToProductId")
    @Mapping(target = "price", ignore = true)
    VariantResponseDTO toResponseDTO(Variant variant);

    @Named("productToProductId")
    default Integer productToProductId(Product product) {
        return product.getId();
    }

    @AfterMapping // or @BeforeMapping
    default void calculatePrice(Variant variant, @MappingTarget VariantResponseDTO dto) {
        dto.setPrice(variant.getProduct().getPricePerUnit().multiply(variant.getStockMultiplier()));

    }

}