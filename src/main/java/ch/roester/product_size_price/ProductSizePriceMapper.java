package ch.roester.product_size_price;

import ch.roester.mapper.EntityMapper;
import ch.roester.unit.Unit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ProductSizePriceMapper extends EntityMapper<ProductSizePriceRequestDTO, ProductSizePriceResponseDTO, ProductSizePrice> {

    @Override
    @Mapping(target = "unit", source = "unit", qualifiedByName = "fromEntityUnitToDtoUnit")
    ProductSizePriceResponseDTO toResponseDTO(ProductSizePrice productSizePrice);

    @Override
    @Mapping(target = "unit", source = "unit", qualifiedByName = "fromDtoUnitToEntityUnit")
    ProductSizePrice fromRequestDTO(ProductSizePriceRequestDTO requestDTO);

    @Named("fromEntityUnitToDtoUnit")
    default String unitToUnit(Unit unit) {
        return unit.getName();
    }

    @Named("fromDtoUnitToEntityUnit")
    default Unit unitToUnit(String unitName) {
        Unit unit = new Unit();
        unit.setName(unitName);
        return unit;
    }
}
