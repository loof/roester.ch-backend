package ch.roester.variant;

import ch.roester.mapper.EntityMapper;
import ch.roester.product.Product;
import ch.roester.unit.Unit;
import ch.roester.unit.UnitMapper;
import ch.roester.unit.UnitRequestDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = UnitMapper.class)
public interface VariantMapper extends EntityMapper<VariantRequestDTO, VariantResponseDTO, Variant> {

    @Override
    @Mapping(target = "productId", source = "product", qualifiedByName = "productToProductId")
    @Mapping(target = "price", ignore = true)
    @Mapping(target = "productName", source = "product", qualifiedByName = "productToProductName")
    VariantResponseDTO toResponseDTO(Variant variant);

    @Override
    @Mapping(target = "displayUnit", source = "displayUnitId", qualifiedByName = "displayUnitIdToDisplayUnit")
    Variant fromRequestDTO(VariantRequestDTO dto);

    @Named("displayUnitIdToDisplayUnit")
    default Unit displayUnitIdToDisplayUnit(Integer displayUnitId) {
        Unit unit = new Unit();
        unit.setId(displayUnitId);
        return unit;
    }

    @Named("productToProductId")
    default Integer productToProductId(Product product) {
        return product.getId();
    }

    @Named("productToProductName")
    default String productToProductName(Product product) {
        return product.getName();
    }

    @AfterMapping
    default void calculatePrice(Variant variant, @MappingTarget VariantResponseDTO dto) {
        dto.setPrice(variant.getProduct().getPricePerUnit().multiply(variant.getStockMultiplier()));
    }

}